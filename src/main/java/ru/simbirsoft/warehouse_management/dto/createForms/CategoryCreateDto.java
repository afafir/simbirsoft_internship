package ru.simbirsoft.warehouse_management.dto.createForms;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoryCreateDto {
    @NotNull
    @NotEmpty(message = "Please provide a name")
    private String name;
    @NotNull
    @NotEmpty(message = "Please provide a description")
    private String description;
}
