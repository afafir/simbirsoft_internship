package ru.simbirsoft.warehouse_management.dto.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import ru.simbirsoft.warehouse_management.dto.WarehouseDto;
import ru.simbirsoft.warehouse_management.model.Warehouse;

import java.util.List;

/** Used for transformation from warheouse DTO to warehouse Entity and backwards. */
@Mapper(componentModel = "spring", uses = ItemWarehouseMapper.class)
public interface WarehouseMapper {
  Warehouse map(WarehouseDto warehouseDto);

  List<Warehouse> mapList(List<WarehouseDto> warehouseDtos);

  @InheritInverseConfiguration
  WarehouseDto mapToDto(Warehouse warehouse);

  @InheritInverseConfiguration
  List<WarehouseDto> mapListDto(List<Warehouse> warehouses);
}
