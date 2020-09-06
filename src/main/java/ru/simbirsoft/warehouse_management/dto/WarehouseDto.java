package ru.simbirsoft.warehouse_management.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.warehouse_management.model.Warehouse;

import java.util.List;
import java.util.stream.Collectors;

/**
 * WarehouseDto is used to transfer data about Warehouser from Repository layer to Controller layer
 * Contains field of address of warehouse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WarehouseDto {

  @ApiModelProperty(
          value = "unique identifier of the warehouse",
          name = "id",
          dataType = "Long",
          example = "42")
  private Long id;

  @ApiModelProperty(
          value = "address of the warehouse",
          name = "address",
          dataType = "String",
          example = "USA, New-York, Wall Street")
  private String address;

  @ApiModelProperty(
          value = "items of warehouse",
          name = "items"
  )
  private List<ItemWarehouseDto> items;
}
