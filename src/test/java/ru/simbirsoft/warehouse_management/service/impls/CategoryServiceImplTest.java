package ru.simbirsoft.warehouse_management.service.impls;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.simbirsoft.warehouse_management.dto.CategoryDto;
import ru.simbirsoft.warehouse_management.dto.createForms.CategoryCreateDto;
import ru.simbirsoft.warehouse_management.exception.NotFoundException;
import ru.simbirsoft.warehouse_management.model.Category;
import ru.simbirsoft.warehouse_management.repository.CategoryRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @InjectMocks
    CategoryServiceImpl categoryServiceImpl;

    @Mock
    CategoryRepository categoryRepository;
    @Test
    void createCategory() {
        CategoryCreateDto categoryCreateDto = new CategoryCreateDto("name", "desc");
        Category category = Category.builder()
                .name(categoryCreateDto.getName())
                .description(categoryCreateDto.getDescription())
                .build();
        Mockito.doReturn(new Category(1L , "name", "desc")).when(categoryRepository).save(category);
        Category test = categoryServiceImpl.createCategory(categoryCreateDto);
        Mockito.verify(categoryRepository, Mockito.times(1)).save(category);
        assertEquals(category.getName(), test.getName());
    }

    @Test
    void getAllCategories() {
        Category one = Category.builder()
                .description("one")
                .name("name")
                .build();
        Category two = Category.builder()
                .description("two")
                .name("two")
                .build();
        Category three = Category.builder()
                .description("three")
                .name("three")
                .build();
        List<Category> categories = new ArrayList<Category> (Arrays.asList(one, two, three));
        Mockito.doReturn(categories).when(categoryRepository).findAll();
        List<Category> test = categoryServiceImpl.getAllCategories();
        assertEquals(3, test.size());


    }

    @Test
    void getCategoryIfExist() {
        Mockito.doReturn(true).when(categoryRepository).existsById(1L);
        Mockito.doReturn(Optional.of(new Category(1L , "name", "desc"))).when(categoryRepository).findById(1L);
        Category test = categoryServiceImpl.getCategory(1L);
            assertEquals(1L, test.getId());
            assertEquals("name", test.getName());
            assertEquals("desc", test.getDescription());
    }

    @Test
    void getCategoryIfNotExist() {
        Mockito.doReturn(false).when(categoryRepository).existsById(1L);
        assertThrows(NotFoundException.class, () -> categoryServiceImpl.getCategory(1L));

    }

    @Test
    void deleteCategoryIfExist() {
        Long id = 12L;
        Mockito.doReturn(true).when(categoryRepository).existsById(id);
        categoryServiceImpl.deleteCategory(id);
        Mockito.verify(categoryRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    void deleteCategoryIfNotExist() {
        Long id = 12L;
        Mockito.doReturn(false).when(categoryRepository).existsById(id);
        assertThrows(NotFoundException.class, () -> categoryServiceImpl.deleteCategory(id));
    }

    @Test
    void updateCategoryIfExist() {
        CategoryDto categoryDto = CategoryDto.builder()
                .id(1L)
                .description("desc")
                .name("name")
                .build();
        Mockito.doReturn(true).when(categoryRepository).existsById(categoryDto.getId());
        Mockito.doReturn(Optional.of(new Category(1L, "name1", "desc1"))).when(categoryRepository).findById(categoryDto.getId());
        Category category = categoryServiceImpl.updateCategory(categoryDto);
        assertEquals(categoryDto.getId(), category.getId());
        assertEquals(categoryDto.getDescription(), category.getDescription());
        assertEquals(categoryDto.getName(), category.getName());

    }
}