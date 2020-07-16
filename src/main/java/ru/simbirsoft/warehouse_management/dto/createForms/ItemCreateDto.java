package ru.simbirsoft.warehouse_management.dto.createForms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ApiModel(description = "Form for item create")
public class ItemCreateDto {
  @ApiModelProperty(
      value = "code of the item",
      name = "code",
      dataType = "String",
      example = "BH-1123")
  @NotNull
  @NotEmpty(message = "Please provide a code")
  private String code;

  @ApiModelProperty(
      value = "name of the item",
      name = "name",
      dataType = "String",
      example = "robot toy")
  @NotNull
  @NotEmpty(message = "Please provide a name")
  private String name;

  @ApiModelProperty(
      value = "description of the item",
      name = "description",
      dataType = "String",
      example = "Its robot toy for children from 3 years old")
  @NotNull
  @NotEmpty(message = "Please provide a description")
  private String description;

  @ApiModelProperty(
      value = "price of the item",
      name = "price",
      dataType = "float",
      example = "115.2")
  @NotNull(message = "Please provide a price")
  private float price;

  @ApiModelProperty(
      value = "categories id of the item",
      name = "categories",
      example = "[1,\n 2,\n 4 \n]")
  @NotNull
  @NotEmpty(message = "Please provide a categories id")
  private List<Long> categories;
}
