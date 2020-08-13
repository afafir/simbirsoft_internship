package ru.simbirsoft.warehouse_management.dto.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.simbirsoft.warehouse_management.dto.ItemInvoiceDto;
import ru.simbirsoft.warehouse_management.dto.ItemWarehouseDto;
import ru.simbirsoft.warehouse_management.model.ItemInvoice;
import ru.simbirsoft.warehouse_management.model.ItemWarehouse;

@Mapper(componentModel = "spring")
public interface ItemWarehouseMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "count", target = "count")
    @Mapping(target = "warehouse", ignore = true)
    ItemWarehouse map(ItemWarehouseDto itemWarehouseDto);

    @InheritInverseConfiguration
    @Mapping(target = "warehouse", ignore = true)
    ItemWarehouseDto mapToDto(ItemWarehouse itemWarehouse);
}
