package ru.simbirsoft.warehouse_management.dto;

import io.swagger.annotations.ApiModelProperty;
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
  @ApiModelProperty(
          value = "unique identifier of the order",
          name = "id",
          dataType = "Long",
          example = "42")
  private Long id;
  @ApiModelProperty(
          value = "customer who makes order",
          name = "customer"
  )
  private UserDto customer;
  @ApiModelProperty(
          value = "Items and counts for this order",
          name = "items"
  )
  private List<OrderItemDto> orderItems;
  @ApiModelProperty(
          value = "Shop the order is linked to",
          name = "shop"
  )
  private ShopDto shop;
  @ApiModelProperty(
          value = "Flag of confirmation of th order",
          name = "confirm"
  )
  private Boolean isConfirmed;
  @ApiModelProperty(
          value = "Time when order was confirmed",
          name = "orderedAt"
  )
  private LocalDateTime orderedAt;
  private float cost;
}
