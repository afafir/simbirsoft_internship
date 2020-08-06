package ru.simbirsoft.warehouse_management.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * WriteoffDto is used to transfer data about Writeoff from Repository layer to Controller layer
 * Contains fields of writeoff
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class WriteoffDto {
  @ApiModelProperty(
      value = "unique identifier of the writeoff",
      name = "id",
      dataType = "Long",
      example = "42")
  private Long id;

  @ApiModelProperty(
          value = "items of the writeoff",
          name = "items")
  private List<ItemWriteoffDto> items;

  @ApiModelProperty(
      value = "confirmation of writeoff ",
      name = "confirmed",
      dataType = "Boolean",
      example = "false")
  private Boolean confirmed;

  @ApiModelProperty(
      value = "The time of the writeoff",
      name = "time",
      dataType = "Local date time",
      example = "2020-20-20-12-12-12")
  private LocalDateTime time;

  @ApiModelProperty(value = "Warehouse where writeoff take place", name = "warehouse")
  private WarehouseDto warehouse;
}
