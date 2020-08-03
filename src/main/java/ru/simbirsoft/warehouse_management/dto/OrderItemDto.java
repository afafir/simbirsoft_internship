package ru.simbirsoft.warehouse_management.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.warehouse_management.model.OrderItem;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDto {
  @ApiModelProperty(
          value = "Order",
          name = "order"
  )
  private OrderDto order;
  @ApiModelProperty(
          value = "Item in the order",
          name = "item"
  )
  private ItemDto item;
  @ApiModelProperty(
          value = "count of item in order",
          name = "count"
  )
  private int count;
}
