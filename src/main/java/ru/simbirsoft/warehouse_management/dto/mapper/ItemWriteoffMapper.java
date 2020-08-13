package ru.simbirsoft.warehouse_management.dto.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.simbirsoft.warehouse_management.dto.ItemWriteoffDto;
import ru.simbirsoft.warehouse_management.model.ItemWriteoff;

@Mapper(componentModel = "spring")
public interface ItemWriteoffMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(source = "count", target = "count")
  ItemWriteoff map(ItemWriteoffDto itemWriteoffDto);

  @InheritInverseConfiguration
  @Mapping(target = "writeoffDto.items", ignore = true)
  @Mapping(target = "writeoffDto.warehouse.items", ignore = true)
  ItemWriteoffDto mapToDto(ItemWriteoff itemWriteoff);
}
