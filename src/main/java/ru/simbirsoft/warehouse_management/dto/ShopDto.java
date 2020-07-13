package ru.simbirsoft.warehouse_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.warehouse_management.model.Order;
import ru.simbirsoft.warehouse_management.model.Shop;
import ru.simbirsoft.warehouse_management.model.Warehouse;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopDto {

    private Long id;
    private String name;
    private WarehouseDto warehouseDto;

    public static ShopDto from(Shop shop){
        return ShopDto.builder()
                .id(shop.getId())
                .name(shop.getName())
                .warehouseDto(WarehouseDto.from(shop.getWarehouse()))
                .build();
    }

}
