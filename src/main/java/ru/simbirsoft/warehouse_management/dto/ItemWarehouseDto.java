package ru.simbirsoft.warehouse_management.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemWarehouseDto {
    private ItemDto item;
    private WarehouseDto warehouse;
    private Integer count;
}
