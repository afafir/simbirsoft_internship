package ru.simbirsoft.warehouse_management.dto.createForms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.simbirsoft.warehouse_management.model.user.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Data
@Builder
@ApiModel("Form for user sign up")
public class SignUpDto {
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

  @Email(message = "Please provide a valid email")
  @NotNull
  @NotEmpty(message = "Please provide a email")
  @ApiModelProperty(
      value = "email of the user",
      name = "email",
      dataType = "String",
      example = "email@gmail.com")
  private String email;

  @NotNull
  @ApiModelProperty(
      value = "role of the user",
      dataType = "string",
      allowableValues = "customer, admin, warehouse_keeper",
      example = "customer")
  private Role role;
}
