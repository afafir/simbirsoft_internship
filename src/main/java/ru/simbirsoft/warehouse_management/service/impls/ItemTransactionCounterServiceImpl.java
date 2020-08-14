package ru.simbirsoft.warehouse_management.service.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.simbirsoft.warehouse_management.exception.NotFoundException;
import ru.simbirsoft.warehouse_management.model.*;
import ru.simbirsoft.warehouse_management.repository.OrderRepository;
import ru.simbirsoft.warehouse_management.repository.ShopRepository;
import ru.simbirsoft.warehouse_management.repository.WarehouseRepository;
import ru.simbirsoft.warehouse_management.repository.WriteoffRepository;
import ru.simbirsoft.warehouse_management.service.ItemTransactionCounterService;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ItemTransactionCounterServiceImpl implements ItemTransactionCounterService {

  private final OrderRepository orderRepository;
  private final ShopRepository shopRepository;
  private final WriteoffRepository writeoffRepository;
  private final WarehouseRepository warehouseRepository;

  @Override
  public Map<Item, Integer> getCountOfSaledItemsForOneShop(
      LocalDateTime start, LocalDateTime end, Long shopId) {
    if (!shopRepository.existsById(shopId)) {
      throw new NotFoundException(Shop.class, "id", String.valueOf(shopId));
    }
    List<Order> orders = orderRepository.findAllConfirmedBetweenDateForShop(start, end, shopId);
    Map<Item, Integer> itemsForShop = new LinkedHashMap<>();
    for (Order order : orders) {
      for (OrderItem item : order.getItems()) {
        if (!itemsForShop.containsKey(item.getItem())) {
          itemsForShop.put(item.getItem(), item.getCount());
        } else {
          itemsForShop.put(item.getItem(), itemsForShop.get(item.getItem()) + item.getCount());
        }
      }
    }
    return itemsForShop;
  }

  @Override
  public Map<Shop, Map<Item, Integer>> getCountOfSaledItemsForAllShop(
      LocalDateTime start, LocalDateTime end) {
    List<Order> orders = orderRepository.findAllConfirmedBetweenDate(start, end);
    Map<Shop, Map<Item, Integer>> itemsForShops = new LinkedHashMap<>();
    for (Order order : orders) {
      if (!itemsForShops.containsKey(order.getShop())) {
        itemsForShops.put(order.getShop(), new LinkedHashMap<>());
      }
      Map<Item, Integer> itemsForShop = itemsForShops.get(order.getShop());
      for (OrderItem orderItem : order.getItems()) {
        if (!itemsForShop.containsKey(orderItem.getItem())) {
          itemsForShop.put(orderItem.getItem(), orderItem.getCount());
        } else {
          itemsForShop.put(
              orderItem.getItem(), itemsForShop.get(orderItem.getItem()) + orderItem.getCount());
        }
      }
    }
    return itemsForShops;
  }

  @Override
  public Map<Warehouse, Map<Item, Integer>> getCountOfWriteoffedItemsForAllWarehouses(
      LocalDateTime start, LocalDateTime end) {
    List<Writeoff> writeoffs = writeoffRepository.findAllBetweenDates(start, end);
    Map<Warehouse, Map<Item, Integer>> writeoffedItemsForWarehouses = new LinkedHashMap<>();
    for (Writeoff writeoff : writeoffs) {
      if (!writeoffedItemsForWarehouses.containsKey(writeoff.getWarehouse())) {
        writeoffedItemsForWarehouses.put(writeoff.getWarehouse(), new LinkedHashMap<>());
      }
      Map<Item, Integer> writeoffedItemsForWarehouse =
          writeoffedItemsForWarehouses.get(writeoff.getWarehouse());
      for (ItemWriteoff itemWriteoff : writeoff.getItems()) {
        if (!writeoffedItemsForWarehouse.containsKey(itemWriteoff.getItem())) {
          writeoffedItemsForWarehouse.put(itemWriteoff.getItem(), itemWriteoff.getCount());
        } else {
          writeoffedItemsForWarehouse.put(
              itemWriteoff.getItem(),
              writeoffedItemsForWarehouse.get(itemWriteoff.getItem()) + itemWriteoff.getCount());
        }
      }
    }
    return writeoffedItemsForWarehouses;
  }

  @Override
  public Map<Item, Integer> getCountOfWriteoffedItemsForOneWarehouses(
      LocalDateTime start, LocalDateTime end, Long warehouseId) {
    if (!warehouseRepository.existsById(warehouseId)) {
      throw new NotFoundException(Warehouse.class, "id", String.valueOf(warehouseId));
    }
    List<Writeoff> writeoffs = writeoffRepository.findAllBetweenDatesForWarehouse(start, end, warehouseId);
    Map<Item, Integer> writeoffedItemsForWarehouse = new LinkedHashMap<>();
    for (Writeoff writeoff : writeoffs) {
      for (ItemWriteoff item : writeoff.getItems()) {
        if (!writeoffedItemsForWarehouse.containsKey(item.getItem())) {
          writeoffedItemsForWarehouse.put(item.getItem(), item.getCount());
        } else {
          writeoffedItemsForWarehouse.put(item.getItem(), writeoffedItemsForWarehouse.get(item.getItem()) + item.getCount());
        }
      }
    }
    return writeoffedItemsForWarehouse;
  }
}
