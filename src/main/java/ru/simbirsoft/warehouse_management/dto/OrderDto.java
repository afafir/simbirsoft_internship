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
}
