package ru.simbirsoft.warehouse_management.dto.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import ru.simbirsoft.warehouse_management.dto.CategoryDto;
import ru.simbirsoft.warehouse_management.model.Category;
import java.util.List;

/** Used for transformation from category DTO to category Entity and backwards. */
@Mapper(componentModel = "spring")
public interface CategoryMapper {
  Category map(CategoryDto category);

  List<Category> mapList(List<CategoryDto> categories);

  @InheritInverseConfiguration
  CategoryDto mapToDto(Category category);

  @InheritInverseConfiguration
  List<CategoryDto> mapListDto(List<Category> categories);
}
