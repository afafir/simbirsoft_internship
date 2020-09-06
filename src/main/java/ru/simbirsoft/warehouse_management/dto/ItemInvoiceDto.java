package ru.simbirsoft.warehouse_management.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.warehouse_management.model.ItemInvoice;
import ru.simbirsoft.warehouse_management.model.OrderItem;

import java.util.List;
import java.util.stream.Collectors;


/**
 * ItemInvoiceDto is used to transfer data about Many-to-Many ItemInvoice from Repository layer to Controller layer
 * Contains fields of ItemInvoice Dto
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemInvoiceDto {
  @ApiModelProperty(
          name = "item"
  )
  private ItemDto item;
  @ApiModelProperty(
          name = "invoice"
  )
  private InvoiceDto invoice;
  @ApiModelProperty(
          name = "count",
          value = "count of the item in invoice",
          dataType = "Integer",
          example = "42"
  )
  private Integer count;
}
