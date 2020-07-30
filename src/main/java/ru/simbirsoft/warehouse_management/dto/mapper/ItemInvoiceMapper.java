package ru.simbirsoft.warehouse_management.dto.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.simbirsoft.warehouse_management.dto.ItemInvoiceDto;
import ru.simbirsoft.warehouse_management.model.ItemInvoice;


@Mapper(componentModel = "spring")
public interface ItemInvoiceMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(source = "count", target = "count")
  ItemInvoice map(ItemInvoiceDto itemInvoiceDto);

  @InheritInverseConfiguration
  @Mapping(target = "invoice.items", ignore = true)
  ItemInvoiceDto mapToDto(ItemInvoice itemInvoice);
}
