package ru.simbirsoft.warehouse_management.service.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.simbirsoft.warehouse_management.dto.CategoryDto;
import ru.simbirsoft.warehouse_management.dto.createForms.CategoryCreateDto;
import ru.simbirsoft.warehouse_management.exception.NotFoundException;
import ru.simbirsoft.warehouse_management.model.Category;
import ru.simbirsoft.warehouse_management.repository.CategoryRepository;
import ru.simbirsoft.warehouse_management.service.CategoryService;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category createCategory(CategoryCreateDto categoryCreateDto) {
        Category category = Category.builder()
                .description(categoryCreateDto.getDescription())
                .name(categoryCreateDto.getName())
                .build();
        category = categoryRepository.save(category);
        return category;
    }


    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategory(Long id) {
        if (categoryRepository.existsById(id)) {
            return categoryRepository.findById(id).get();
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
    public Category updateCategory(CategoryDto categoryDto) {
        if (categoryRepository.existsById(categoryDto.getId())) {
            Category category = categoryRepository.findById(categoryDto.getId()).get();
            category.setDescription(categoryDto.getDescription());
            category.setName(categoryDto.getName());
            return category;
        } else throw new NotFoundException(Category.class, "id", String.valueOf(categoryDto.getId()));
    }


}
