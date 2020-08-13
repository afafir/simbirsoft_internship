package ru.simbirsoft.warehouse_management.dto.createForms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ApiModel(description = "Form for shop create")
public class ShopCreateDto {

  @ApiModelProperty(
      value = "id of the warehouse the shop is linked to",
      name = "warehouse id",
      dataType = "Long",
      example = "42")
  @NotNull
  private Long warehouseId;

  @ApiModelProperty(
      value = "name of the shop",
      name = "shop",
      dataType = "String",
      example = "Super shop")
  @NotNull
  @NotEmpty(message = "Please provide a name")
  private String name;
}
