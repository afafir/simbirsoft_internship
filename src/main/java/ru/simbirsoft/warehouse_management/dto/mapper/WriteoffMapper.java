package ru.simbirsoft.warehouse_management.dto.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import ru.simbirsoft.warehouse_management.dto.WriteoffDto;
import ru.simbirsoft.warehouse_management.model.Writeoff;

import java.util.List;

@Mapper(
    componentModel = "spring",
    uses = {ItemWriteoffMapper.class, WarehouseMapper.class})
public interface WriteoffMapper {

  Writeoff map(WriteoffDto writeoffDto);

  List<Writeoff> mapList(List<WriteoffDto> writeoffDtos);

  @InheritInverseConfiguration
  WriteoffDto mapToDto(Writeoff writeoff);

  @InheritInverseConfiguration
  List<WriteoffDto> mapListDto(List<Writeoff> writeoffs);
}
