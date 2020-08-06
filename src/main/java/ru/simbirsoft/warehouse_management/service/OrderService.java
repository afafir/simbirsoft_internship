package ru.simbirsoft.warehouse_management.service;

import ru.simbirsoft.warehouse_management.dto.OrderDto;
import ru.simbirsoft.warehouse_management.dto.createForms.OrderCreateForm;

import java.util.List;

public interface OrderService {
    OrderDto createOrder(OrderCreateForm orderCreateForm);

    OrderDto getById(Long orderId);

    OrderDto addItemToOrder(Long orderId, Long itemId, Integer count);

    OrderDto deleteItemFromOrder(Long orderId, Long itemId, Integer count);

    OrderDto confirmOrder(Long orderId);

    OrderDto declineOrder(Long orderId);

    List<OrderDto> getAllOrderForUser(Long userId);

    List<OrderDto> getAllOrders();

    OrderDto getLastUnconfirmedOrderForUser(Long userId);
}
