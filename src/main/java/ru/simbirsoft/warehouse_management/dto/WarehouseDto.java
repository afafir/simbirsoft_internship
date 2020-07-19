package ru.simbirsoft.warehouse_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.warehouse_management.model.Warehouse;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WarehouseDto {
    private Long id;
    private String address;

    public static WarehouseDto from (Warehouse warehouse){
        return WarehouseDto.builder()
                .id(warehouse.getId())
                .address(warehouse.getAddress())
                .build();
    }

    public static List<WarehouseDto> from (List<Warehouse> warehouses){
        return warehouses.stream().map(WarehouseDto::from).collect(Collectors.toList());
    }

}
