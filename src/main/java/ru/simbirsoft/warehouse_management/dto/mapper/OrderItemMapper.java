package ru.simbirsoft.warehouse_management.dto.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import ru.simbirsoft.warehouse_management.dto.OrderItemDto;
import ru.simbirsoft.warehouse_management.model.Order;
import ru.simbirsoft.warehouse_management.model.OrderItem;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

  OrderItem map(OrderItemDto orderItemDto);

  List<OrderItem> mapToList(List<OrderItemDto> orderItemDtos);

  @InheritInverseConfiguration
  OrderItemDto mapToDto(OrderItem orderItem);

  @InheritInverseConfiguration
  List<OrderItemDto> mapToListDto(List<OrderItem> orderItems);

}
