package ru.simbirsoft.warehouse_management.dto.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import ru.simbirsoft.warehouse_management.dto.SupplierDto;
import ru.simbirsoft.warehouse_management.model.Supplier;

import java.util.List;

/** Used for transformation from supplier DTO to supplier Entity and backwards. */
@Mapper(componentModel = "spring")
public interface SupplierMapper {
    Supplier map(SupplierDto supplierDto);

    List<Supplier> mapList(List<SupplierDto> supplierDtos);

    @InheritInverseConfiguration
    SupplierDto mapToDto(Supplier supplier);

    @InheritInverseConfiguration
    List<SupplierDto> mapListDto(List<Supplier> suppliers);
}
