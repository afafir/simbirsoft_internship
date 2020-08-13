package ru.simbirsoft.warehouse_management.service.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.simbirsoft.warehouse_management.dto.OrderDto;
import ru.simbirsoft.warehouse_management.dto.createForms.OrderCreateForm;
import ru.simbirsoft.warehouse_management.dto.mapper.OrderMapper;
import ru.simbirsoft.warehouse_management.dto.mapper.ShopMapper;
import ru.simbirsoft.warehouse_management.dto.mapper.UserMapper;
import ru.simbirsoft.warehouse_management.exception.NotFoundException;
import ru.simbirsoft.warehouse_management.model.*;
import ru.simbirsoft.warehouse_management.model.pk.ItemWarehousePk;
import ru.simbirsoft.warehouse_management.model.pk.OrderItemPk;
import ru.simbirsoft.warehouse_management.model.user.User;
import ru.simbirsoft.warehouse_management.repository.OrderItemRepository;
import ru.simbirsoft.warehouse_management.repository.OrderRepository;
import ru.simbirsoft.warehouse_management.service.OrderService;
import ru.simbirsoft.warehouse_management.service.ShopService;
import ru.simbirsoft.warehouse_management.service.UserService;
import ru.simbirsoft.warehouse_management.service.WarehouseService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

  // services
  private final UserService userService;
  private final ShopService shopService;
  private final WarehouseService warehouseService;

  // mappers
  private final UserMapper userMapper;
  private final ShopMapper shopMapper;
  private final OrderMapper orderMapper;

  // repositories
  private final OrderRepository orderRepository;
  private final OrderItemRepository orderItemRepository;

  @Override
  public OrderDto createOrder(OrderCreateForm orderCreateForm) {
    // проверяем, нет ли у пользователя незавершенного заказа
    if (orderRepository.existsByCustomerIdAndIsConfirmedFalse(orderCreateForm.getCustomerId())) {
      throw new IllegalStateException("Order already exist");
    }
    User customer = userMapper.map(userService.getById(orderCreateForm.getCustomerId()));
    Shop shop = shopMapper.map(shopService.getById(orderCreateForm.getShopId()));
    Order order = Order.builder().customer(customer).shop(shop).isConfirmed(false).build();
    return orderMapper.mapToDto(orderRepository.save(order));
  }

  @Override
  public OrderDto getById(Long orderId) {
    if (orderRepository.existsById(orderId)) {
      return orderMapper.mapToDto(orderRepository.getOne(orderId));
    } else throw new NotFoundException(Order.class, "id", String.valueOf(orderId));
  }

  @Override
  @Transactional
  public OrderDto addItemToOrder(Long orderId, Long itemId, Integer count) {
    Order order = orderMapper.map(getById(orderId));
    if (order.getIsConfirmed()) {
      throw new IllegalStateException("Order already confirmed");
    }
    // находим итем на складе
    ItemWarehouse itemWarehouse =
        warehouseService.getItemFromWarehouse(order.getShop().getWarehouse().getId(), itemId);
    OrderItem orderItem;
    // проверяем, в достаточном ли количестве вещей на складе
    if (itemWarehouse.getCount() >= count) {
      OrderItemPk orderItemId = new OrderItemPk(itemId, orderId);
      // меняем количество вещей на складе
      itemWarehouse.setCount(itemWarehouse.getCount() - count);
      // если эта вещь уже была в заказе, то добавляем к существующему количеству еще вещей
      if (orderItemRepository.existsById(orderItemId)) {
        orderItem = orderItemRepository.getOne(orderItemId);
        order.getItems().remove(orderItem);
        orderItem.setCount(orderItem.getCount() + count);
        order.getItems().add(orderItem);
      } else {
        // если вещей не было в заказе, создаем новую запись
        orderItem =
            OrderItem.builder()
                .item(itemWarehouse.getItem())
                .id(new OrderItemPk(itemId, orderId))
                .order(order)
                .count(count)
                .build();
        orderItemRepository.save(orderItem);
        order.getItems().add(orderItem);
      }
      return orderMapper.mapToDto(order);
    } else {
      throw new IllegalStateException("There aren't that many items in the warehouse");
    }
  }

  @Override
  @Transactional
  public OrderDto deleteItemFromOrder(Long orderId, Long itemId, Integer count) {
    OrderItemPk orderItemPk = new OrderItemPk(itemId, orderId);
    Order order = orderMapper.map(getById(orderId));
    if (order.getIsConfirmed()) {
      throw new IllegalStateException("Order already confirmed");
    }
    // смотрим, есть ли итем в заказе
    if (orderItemRepository.existsById(orderItemPk)) {
      // находим итем на складе
      ItemWarehouse itemWarehouse =
          warehouseService.getItemFromWarehouse(order.getShop().getWarehouse().getId(), itemId);
      OrderItem orderItem = orderItemRepository.getOne(orderItemPk);
      // смотрим, не превышает ли удаляемое количество общее количество
      if (orderItem.getCount() > count) {
        order.getItems().remove(orderItem);
        orderItem.setCount(orderItem.getCount() - count);
        order.getItems().add(orderItem);
      } else if (orderItem.getCount().equals(count)) {
        order.getItems().remove(orderItem);
        orderItemRepository.deleteById(orderItemPk);
      } else {
        throw new IllegalStateException("So many items are not in this order");
      }
      // возвращаем на склад удаленные из заказа товары
      itemWarehouse.setCount(itemWarehouse.getCount() + count);
    } else throw new IllegalStateException("There is no such item in the order");
    return orderMapper.mapToDto(order);
  }

  @Override
  @Transactional
  public OrderDto confirmOrder(Long orderId) {
    Optional<Order> orderOpt = orderRepository.findById(orderId);
    if (orderOpt.isPresent()) {
      Order order = orderOpt.get();
      if (!order.getIsConfirmed()) {
        order.setIsConfirmed(true);
        order.setOrderedAt(LocalDateTime.now());
        return orderMapper.mapToDto(order);
      } else throw new IllegalStateException("Order already confirmed");
    } else throw new NotFoundException(Order.class, "id", String.valueOf(orderId));
  }

  @Override
  public OrderDto declineOrder(Long orderId) {
    Order order = orderMapper.map(getById(orderId));
    if (order.getIsConfirmed()) {
      throw new IllegalStateException("You cant decline already confirmed orders");
    }
    List<ItemWarehouse> toReturn =
        order.getItems().stream()
            .map(
                x ->
                    ItemWarehouse.builder()
                        .id(
                            new ItemWarehousePk(
                                x.getItem().getId(), order.getShop().getWarehouse().getId()))
                        .item(x.getItem())
                        .warehouse(order.getShop().getWarehouse())
                        .count(x.getCount())
                        .build())
            .collect(Collectors.toList());
    // возвращаем вещи на склад
    warehouseService.putAllItems(toReturn);
    orderRepository.delete(order);
    return orderMapper.mapToDto(order);
  }

  @Override
  public List<OrderDto> getAllOrderForUser(Long userId) {
    return orderMapper.mapToListDto(orderRepository.findByCustomerId(userId));
  }

  @Override
  public List<OrderDto> getAllOrders() {
    return orderMapper.mapToListDto(orderRepository.findAll());
  }

  @Override
  public OrderDto getLastUnconfirmedOrderForUser(Long userId) {
    Optional<Order> optionalOrder = orderRepository.findByCustomerIdAndIsConfirmedFalse(userId);
    if (optionalOrder.isPresent()) {
      return orderMapper.mapToDto(optionalOrder.get());
    } else throw new IllegalStateException("There are no unconfirmed orders for user");
  }
}
