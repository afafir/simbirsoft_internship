package ru.simbirsoft.warehouse_management.dto.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.simbirsoft.warehouse_management.dto.ShopDto;
import ru.simbirsoft.warehouse_management.model.Shop;

import java.util.List;


/** Used for transformation from shop DTO to shop Entity and backwards. */
@Mapper(
    componentModel = "spring",
    uses = {WarehouseMapper.class, OrderMapper.class})
public interface ShopMapper {

    @Mapping(source = "warehouseDto",target ="warehouse")
    @Mapping(source = "orderDtos", target = "orders")
    Shop map(ShopDto shopDto);

    List<Shop> mapToList(List<ShopDto> shopDtos);

    @InheritInverseConfiguration
    ShopDto mapToDto(Shop shop);

    @InheritInverseConfiguration
    List<ShopDto> mapToListDto(List<Shop> shops);

}
