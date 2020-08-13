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
@ApiModel(description = "Form for order create")
public class OrderCreateForm {
  @ApiModelProperty(
      value = "id of the shop the order is linked to",
      name = "shop id",
      dataType = "Long",
      example = "42")
  @NotNull
  private Long shopId;

  @ApiModelProperty(
      value = "Id of the user who makes order. Filled in by the server using jwt token",
      name = "customer id",
      dataType = "Long",
      example = "1",
      hidden = true)
  private Long customerId;
}
