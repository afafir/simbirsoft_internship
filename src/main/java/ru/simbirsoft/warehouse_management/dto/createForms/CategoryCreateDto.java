package ru.simbirsoft.warehouse_management.dto.createForms;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ApiModel(description = "Form for categories create")
public class CategoryCreateDto {
  @ApiModelProperty(
          value = "name of the category",
          name = "name",
          dataType = "String",
          example = "toy"
  )
  @NotNull
  @NotEmpty(message = "Please provide a name")
  private String name;

  @ApiModelProperty(
          value = "description of the category",
          name = "description",
          dataType = "String",
          example = "an object for children to play with"
  )
  @NotNull
  @NotEmpty(message = "Please provide a description")
  private String description;
}
