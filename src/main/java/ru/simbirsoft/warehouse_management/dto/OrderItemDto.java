package ru.simbirsoft.warehouse_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.warehouse_management.model.OrderItem;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDto {

   private ItemDto itemDto;
   private int quantity;

   public static OrderItemDto from(OrderItem orderItem){
      return OrderItemDto.builder()
              .itemDto(ItemDto.from(orderItem.getItem()))
              .quantity(orderItem.getCount())
              .build();
   }

   public static List<OrderItemDto> from(List<OrderItem> orderItems){
      return orderItems.stream().map(OrderItemDto::from).collect(Collectors.toList());
   }
}
