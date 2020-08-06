package ru.simbirsoft.warehouse_management.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.warehouse_management.model.Supplier;

import java.util.List;
import java.util.stream.Collectors;


/**
 * SupplierDto is used to transfer data about Warehouser from Repository layer to Controller layer
 * Contains field of address of warehouse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplierDto {


  @ApiModelProperty(
          value = "unique identifier of the supplier",
          name = "id",
          dataType = "Long",
          example = "42")
  private Long id;

  @ApiModelProperty(
          value = "name of the supplier",
          name = "name",
          dataType = "String",
          example = "super retail group")
  private String name;
}
