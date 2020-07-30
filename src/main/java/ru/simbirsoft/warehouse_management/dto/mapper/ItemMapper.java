package ru.simbirsoft.warehouse_management.dto.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.simbirsoft.warehouse_management.dto.ItemDto;
import ru.simbirsoft.warehouse_management.model.Item;
import java.util.List;

/** Used for transformation from item DTO to item Entity and backwards. */
@Mapper(
    componentModel = "spring",
    uses = {CategoryMapper.class})
public interface ItemMapper {
  @Mapping(target = "categories", source = "categories")
  Item map(ItemDto itemDto);

  List<Item> mapList(List<ItemDto> items);

  @InheritInverseConfiguration
  ItemDto mapToDto(Item item);

  @InheritInverseConfiguration
  List<ItemDto> mapListDto(List<Item> items);
}
