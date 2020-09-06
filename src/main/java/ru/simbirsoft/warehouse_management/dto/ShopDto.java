package ru.simbirsoft.warehouse_management.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.warehouse_management.model.Order;
import ru.simbirsoft.warehouse_management.model.Shop;
import ru.simbirsoft.warehouse_management.model.Warehouse;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopDto {
  @ApiModelProperty(
      value = "unique identifier of the shop",
      name = "id",
      dataType = "Long",
      example = "42")
  private Long id;

  @ApiModelProperty(
      value = "name of the shop",
      name = "name",
      dataType = "String",
      example = "SUPER SHOP")
  private String name;

  @ApiModelProperty(
          value = "The warehouse that the store is linked to",
          name = "warehouse")
  private WarehouseDto warehouseDto;


  @ApiModelProperty(
          value = "Orders that were made in this shop",
          name = "ordrers"
  )
  private List<OrderDto> orderDtos;
}
