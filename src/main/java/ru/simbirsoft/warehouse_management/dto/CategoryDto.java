package ru.simbirsoft.warehouse_management.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * CategoryDto is used to transfer data about Category from Repository layer to Controller layer
 * Contains fields of categories
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {

  @ApiModelProperty(
      value = "unique identifier of the categories",
      name = "id",
      dataType = "Long",
      example = "42")
  private Long id;

  @ApiModelProperty(
      value = "name of the categories",
      name = "name",
      dataType = "String",
      example = "toy")
  private String name;

  @ApiModelProperty(
      value = "description of the categories",
      name = "description",
      dataType = "String",
      example = "an object for children to play with")
  private String description;
}
