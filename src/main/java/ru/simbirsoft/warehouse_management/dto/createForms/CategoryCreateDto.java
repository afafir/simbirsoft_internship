package ru.simbirsoft.warehouse_management.dto.createForms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoryCreateDto {
    private String name;
    private String description;
}
