package ru.simbirsoft.warehouse_management.service.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.simbirsoft.warehouse_management.dto.CategoryDto;
import ru.simbirsoft.warehouse_management.dto.createForms.CategoryCreateDto;
import ru.simbirsoft.warehouse_management.dto.mapper.CategoryMapper;
import ru.simbirsoft.warehouse_management.exception.NotFoundException;
import ru.simbirsoft.warehouse_management.model.Category;
import ru.simbirsoft.warehouse_management.repository.CategoryRepository;
import ru.simbirsoft.warehouse_management.service.CategoryService;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  private final CategoryMapper categoryMapper;

  @Override
  public CategoryDto createCategory(CategoryCreateDto categoryCreateDto) {
    Category category =
        Category.builder()
            .description(categoryCreateDto.getDescription())
            .name(categoryCreateDto.getName())
            .build();
    category = categoryRepository.save(category);
    return categoryMapper.mapToDto(category);
  }

  @Override
  public List<CategoryDto> getAllCategories() {
    return categoryRepository.findAll().stream()
        .map(categoryMapper::mapToDto)
        .collect(Collectors.toList());
  }

  @Override
  public CategoryDto getCategory(Long id) {
    if (categoryRepository.existsById(id)) {
      return categoryMapper.mapToDto(categoryRepository.getOne(id));
    } else throw new NotFoundException(Category.class, "id", String.valueOf(id));
  }

  @Override
  public void deleteCategory(Long id) {
    if (categoryRepository.existsById(id)) {
      categoryRepository.deleteById(id);
    } else throw new NotFoundException(Category.class, "id", String.valueOf(id));
  }

  @Override
  @Transactional
  public CategoryDto updateCategory(CategoryDto categoryDto) {
    if (categoryRepository.existsById(categoryDto.getId())) {
      Category category = categoryRepository.getOne(categoryDto.getId());
      category.setDescription(categoryDto.getDescription());
      category.setName(categoryDto.getName());
      return categoryMapper.mapToDto(category);
    } else throw new NotFoundException(Category.class, "id", String.valueOf(categoryDto.getId()));
  }
}
