package ru.simbirsoft.warehouse_management.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.warehouse_management.model.Item;
import java.util.List;
import java.util.stream.Collectors;
/**
 * CategoryDto is used to transfer data about Category from Repository layer to Controller layer
 * Contains fields of category
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "An object containing item information")
public class ItemDto {

  @ApiModelProperty(
      value = "unique identifier of the item",
      name = "id",
      dataType = "Long",
      example = "42")
  private Long id;

  @ApiModelProperty(
      value = "code of the item",
      name = "code",
      dataType = "String",
      example = "BH-1123")
  private String code;

  @ApiModelProperty(
      value = "Categories information of the item",
      name = "categoriesDto",
      dataType = "List")
  private List<CategoryDto> categories;

  @ApiModelProperty(
      value = "Price of the item",
      name = "price",
      dataType = "float",
      example = "42,42")
  private float price;

  @ApiModelProperty(
      value = "Description of the item",
      name = "Description",
      dataType = "Its robot toy for children from 3 years old")
  private String description;

  @ApiModelProperty(
          value =  "Name of the item",
          name = "name",
          dataType = "Robot toy")
  private String name;
}
