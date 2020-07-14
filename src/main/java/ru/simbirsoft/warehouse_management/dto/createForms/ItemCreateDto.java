package ru.simbirsoft.warehouse_management.dto.createForms;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ItemCreateDto {
    @NotNull
    @NotEmpty(message = "Please provide a code")
    private String code;
    @NotNull
    @NotEmpty(message = "Please provide a name")
    private String name;
    @NotNull
    @NotEmpty(message = "Please provide a description")
    private String description;
    @NotNull(message = "Please provide a price")
    private float price;
    @NotNull
    @NotEmpty(message = "Please provide a categories id")
    private List<Long> categories;
}
