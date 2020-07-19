package ru.simbirsoft.warehouse_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.warehouse_management.model.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private Long id;
    private UserDto customer;
    private List<OrderItemDto> orderItems;
    private ShopDto shop;
    private boolean isConfirmed;
    private LocalDateTime orderedAt;

    public static OrderDto from(Order order){
        OrderDto orderDto = OrderDto.builder()
                .id(order.getId())
                .orderItems(OrderItemDto.from(order.getItems()))
                .isConfirmed(order.getIsConfirmed())
                .customer(UserDto.from(order.getCustomer()))
                .build();
        if (order.getOrderedAt() != null){
            orderDto.setOrderedAt(order.getOrderedAt());
        }
        return orderDto;
    }

    public static List<OrderDto> from (List<Order> orders){
        return orders.stream().map(OrderDto::from).collect(Collectors.toList());
    }




}
