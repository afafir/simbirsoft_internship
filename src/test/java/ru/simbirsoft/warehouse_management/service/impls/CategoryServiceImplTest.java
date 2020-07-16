package ru.simbirsoft.warehouse_management.service.impls;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.simbirsoft.warehouse_management.dto.CategoryDto;
import ru.simbirsoft.warehouse_management.dto.createForms.CategoryCreateDto;
import ru.simbirsoft.warehouse_management.dto.mapper.CategoryMapper;
import ru.simbirsoft.warehouse_management.exception.NotFoundException;
import ru.simbirsoft.warehouse_management.model.Category;
import ru.simbirsoft.warehouse_management.repository.CategoryRepository;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @InjectMocks
    CategoryServiceImpl categoryServiceImpl;

    @Mock
    CategoryRepository categoryRepository;
    @Mock
    CategoryMapper categoryMapper;

    Category category;
    CategoryDto categoryDto;

    @BeforeEach
    void init(){
        category = Category.builder()
                .id(1L)
                .name("name")
                .description("desc")
                .build();
        categoryDto = CategoryDto.builder()
                .id(1L)
                .name("name")
                .description("desc")
                .build();
    }

    @Test
    void createCategory() {
        CategoryCreateDto categoryCreateDto = new CategoryCreateDto("name", "desc");
        Category toCreate = Category.builder()
                .name(categoryCreateDto.getName())
                .description(categoryCreateDto.getDescription())
                .build();

        Mockito.doReturn(category).when(categoryRepository).save(toCreate);
        Mockito.doReturn(categoryDto).when(categoryMapper).mapToDto(category);
        CategoryDto test = categoryServiceImpl.createCategory(categoryCreateDto);
        Mockito.verify(categoryRepository, Mockito.times(1)).save(toCreate);
        assertEquals(toCreate.getName(), test.getName());
    }


    @Test
    void getCategoryIfExist() {
        Mockito.doReturn(true).when(categoryRepository).existsById(1L);
        Mockito.doReturn(category).when(categoryRepository).getOne(1L);
        Mockito.doReturn(categoryDto).when(categoryMapper).mapToDto(category);
        CategoryDto test = categoryServiceImpl.getCategory(1L);
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
        Mockito.doReturn(true).when(categoryRepository).existsById(categoryDto.getId());
        Category categoryToUpdate = Category.builder()
                .id(1L)
                .name("name1")
                .description("desc1")
                .build();
        Mockito.doReturn(categoryToUpdate).when(categoryRepository).getOne(categoryDto.getId());
        Mockito.doReturn(categoryDto).when(categoryMapper).mapToDto(categoryToUpdate);
        CategoryDto categoryUpdatedDto = categoryServiceImpl.updateCategory(categoryDto);
        assertEquals(categoryDto.getId(), categoryUpdatedDto.getId());
        assertEquals(categoryDto.getDescription(), categoryUpdatedDto.getDescription());
        assertEquals(categoryDto.getName(), categoryUpdatedDto.getName());

    }
}