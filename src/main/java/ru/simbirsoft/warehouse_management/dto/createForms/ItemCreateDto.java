package ru.simbirsoft.warehouse_management.dto.createForms;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ItemCreateDto {
    private String code;
    private String name;
    private String description;
    private float price;
    private List<Long> categories;
}
