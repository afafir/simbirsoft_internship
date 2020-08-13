package ru.simbirsoft.warehouse_management.dto.mapper;


import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.simbirsoft.warehouse_management.dto.OrderDto;
import ru.simbirsoft.warehouse_management.model.Order;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, ShopMapper.class, OrderItemMapper.class})
public interface OrderMapper {

    @Mapping(target = "shop.orders", ignore = true)
    @Mapping(target = "shop.warehouse", source = "shop.warehouseDto")
    @Mapping(target = "shop.warehouse.items", ignore = true)
    @Mapping(target = "items", source = "orderItems")
    Order map(OrderDto orderDto);

    List<Order> mapToList(List<OrderDto> orderDtoList);

    @InheritInverseConfiguration
    @Mapping(target = "shop.orderDtos", ignore = true)
    @Mapping(target = "shop.warehouseDto.items", ignore = true)
    OrderDto mapToDto(Order order);

    @InheritInverseConfiguration
    List<OrderDto> mapToListDto(List<Order> orders);

}
