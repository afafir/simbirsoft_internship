package ru.simbirsoft.warehouse_management.dto.createForms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@ApiModel("Form for user sign in")
public class SignInDto {

  @ApiModelProperty(
      value = "username",
      name = "username",
      dataType = "String",
      example = "superuser")
  @NotNull
  @NotEmpty(message = "Please provide a username")
  private String username;

  @ApiModelProperty(
      value = "password of the user",
      name = "password",
      dataType = "String",
      example = "supersafepassword007")
  @NotNull
  @NotEmpty(message = "Please provide a name")
  @Size(min = 6, max = 30)
  private String password;
}
