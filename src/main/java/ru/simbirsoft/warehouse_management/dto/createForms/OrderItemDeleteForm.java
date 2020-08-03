package ru.simbirsoft.warehouse_management.dto.createForms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ApiModel(description = "Form for order item remove")
public class OrderItemDeleteForm {

  @ApiModelProperty(value = "id of the item", name = "item id", dataType = "Long", example = "42")
  @NotNull
  Long itemId;

  @ApiModelProperty(value = "id of the item", name = "item id", dataType = "Long", example = "11")
  @NotNull
  Long orderId;

  @ApiModelProperty(
      value = "count of the item to delete",
      name = "count",
      dataType = "Integer",
      example = "1")
  @NotNull
  Integer count;
}
