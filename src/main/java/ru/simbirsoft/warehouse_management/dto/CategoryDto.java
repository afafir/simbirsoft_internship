package ru.simbirsoft.warehouse_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.warehouse_management.model.Category;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
    private String name;

    public static CategoryDto from(Category category){
            return CategoryDto.builder()
                    .name(category.getName())
                    .build();
    }

    public static List<CategoryDto> from(List<Category> categories){
        return categories.stream().map(CategoryDto::from).collect(Collectors.toList());
    }
}
