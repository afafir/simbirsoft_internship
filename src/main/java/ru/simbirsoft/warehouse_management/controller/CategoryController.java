package ru.simbirsoft.warehouse_management.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.warehouse_management.dto.CategoryDto;
import ru.simbirsoft.warehouse_management.dto.createForms.CategoryCreateDto;
import ru.simbirsoft.warehouse_management.model.Category;
import ru.simbirsoft.warehouse_management.service.CategoryService;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "returns all categories from db")
    @GetMapping("/categories")
    private List<CategoryDto> all(){
        return CategoryDto.from(categoryService.getAllCategories());
    }

    @ApiOperation(value = "returns category with such id.If category doesnt exist, returns custom error message ")
    @GetMapping("/categories/{id}")
    private CategoryDto getProduct(@PathVariable Long id){
        return CategoryDto.from(categoryService.getCategory(id));
    }

    @ApiOperation(value = "Creates new category from dto-form and returns Category entity with id")
    @PostMapping("/categories/new")
    private Category newCategory(@RequestBody CategoryCreateDto categoryCreateDto){
        return categoryService.createCategory(categoryCreateDto);
    }

    @ApiOperation(value = "Delete category from db with such id. If category doesnt exist, returns custom error message")
    @DeleteMapping("/categories/{id}")
    private ResponseEntity<String> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<>("Category was deleted successfully",HttpStatus.OK);
    }

    @ApiOperation(value = "Update category replacing fields in the database with fields from the СategoryDto form." +
            " If category doesnt exist, returns custom error message")
    @PutMapping("/categories/update")
    private CategoryDto updateItem(@RequestBody CategoryDto categoryDto){
        return CategoryDto.from(categoryService.updateCategory(categoryDto));
    }
}
