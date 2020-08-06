package ru.simbirsoft.warehouse_management.dto.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import ru.simbirsoft.warehouse_management.dto.InvoiceDto;
import ru.simbirsoft.warehouse_management.model.Invoice;

import java.util.List;

/** Used for transformation from invoice DTO to invoice Entity and backwards. */
@Mapper(
    componentModel = "spring",
    uses = {SupplierMapper.class, WarehouseMapper.class, ItemInvoiceMapper.class})
public interface InvoiceMapper {

  Invoice map(InvoiceDto invoiceDto);

  List<Invoice> mapList(List<InvoiceDto> invoices);

  @InheritInverseConfiguration
  InvoiceDto mapToDto(Invoice invoice);

  @InheritInverseConfiguration
  List<InvoiceDto> mapListDto(List<Invoice> invoices);
}
