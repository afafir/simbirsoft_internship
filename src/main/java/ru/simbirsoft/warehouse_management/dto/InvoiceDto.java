package ru.simbirsoft.warehouse_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InvoiceDto {
  private Long id;
  private SupplierDto supplierDto;
  private List<ItemInvoiceDto> itemsDto;
  private LocalDateTime arrivedAt;
  private WarehouseDto warehouseDto;
}
