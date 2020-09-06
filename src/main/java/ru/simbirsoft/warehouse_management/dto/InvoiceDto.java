package ru.simbirsoft.warehouse_management.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

/**
 * InvoiceDto is used to transfer data about Invoice from Repository layer to Controller layer
 * Contains fields of invoice
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InvoiceDto {
  @ApiModelProperty(
          value = "unique identifier of the invoice",
          name = "id",
          dataType = "Long",
          example = "42")
  private Long id;
  @ApiModelProperty(
          value = "Invoice supplier",
          name = "supplier")
  private SupplierDto supplier;
  @ApiModelProperty(
          value = "items of the invoice",
          name = "items"
  )
  private List<ItemInvoiceDto> items;

  @ApiModelProperty(
          value = "confirmation of invoice ",
          name = "confirmed",
          dataType = "Boolean",
          example = "false")
  private Boolean confirmed;
  @ApiModelProperty(
          value = "The time of arrival of the order",
          name = "arrivedAt",
          dataType = "Local date time",
          example = "2020-20-20-12-12-12")
  private LocalDateTime arrivedAt;
  @ApiModelProperty(
          value = "Warehouse where invoice take place",
          name = "warehouse")
  private WarehouseDto warehouse;
}
