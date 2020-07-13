package ru.simbirsoft.warehouse_management.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.warehouse_management.model.Invoice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InvoiceDto {
    private SupplierDto supplierDto;
    private List<ItemInvoiceDto> itemsDto;
    private LocalDateTime arrivedAt;
    private WarehouseDto warehouseDto;

    public static InvoiceDto from (Invoice invoice){
       return InvoiceDto.builder()
                .arrivedAt(invoice.getArrivedAt())
                .itemsDto(ItemInvoiceDto.from(invoice.getItems()))
                .warehouseDto(WarehouseDto.from(invoice.getWarehouse()))
                .supplierDto(SupplierDto.from(invoice.getSupplier()))
                .build();
    }


    public static List<InvoiceDto> from (List<Invoice> invoices){
        return invoices.stream().map(InvoiceDto::from).collect(Collectors.toList());
    }



}
