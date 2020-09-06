package ru.simbirsoft.warehouse_management.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

@ApiModel
public enum Role {
  @JsonProperty("customer")
  CUSTOMER,
  @JsonProperty("admin")
  ADMIN,
  @JsonProperty("warehouse_keeper")
  WAREHOUSE_KEEPER
}
