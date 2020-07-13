package ru.simbirsoft.warehouse_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.warehouse_management.model.Supplier;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplierDto {

    private Long id;
    private String supplier;

    public static SupplierDto from (Supplier supplier){
        return SupplierDto.builder()
                .id(supplier.getId())
                .supplier(supplier.getName())
                .build();
    }

    public static List<SupplierDto> from (List<Supplier> suppliers){
        return suppliers.stream().map(SupplierDto::from).collect(Collectors.toList());
    }

}
