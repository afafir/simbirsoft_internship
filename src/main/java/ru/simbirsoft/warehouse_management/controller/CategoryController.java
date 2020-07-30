package ru.simbirsoft.warehouse_management.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.warehouse_management.dto.CategoryDto;
import ru.simbirsoft.warehouse_management.dto.createForms.CategoryCreateDto;
import ru.simbirsoft.warehouse_management.service.CategoryService;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api("Operations pertaining to categories information if warehouse")
public class CategoryController {

  private final CategoryService categoryService;

  @ApiOperation(value = "returns all categories from db")
  @GetMapping(value = "${url.category}")
  public List<CategoryDto> all() {
    return categoryService.getAllCategories();
  }

  @ApiOperation(
      value =
          "returns categories with such id.If categories doesnt exist, returns custom error message ")
  @GetMapping("${url.category}/{id}")
  public CategoryDto getProduct(@PathVariable @ApiParam("Id of the categories") Long id) {
    return categoryService.getCategory(id);
  }

  @ApiOperation(value = "Creates new categories from dto-form and returns Category entity with id")
  @PostMapping("${url.category.new}")
  public CategoryDto newCategory(@RequestBody @Valid CategoryCreateDto categoryCreateDto) {
    return categoryService.createCategory(categoryCreateDto);
  }

  @ApiOperation(
      value =
          "Delete categories from db with such id. If categories doesnt exist, returns custom error message")
  @DeleteMapping("${url.category}/{id}")
  public ResponseEntity<String> deleteCategory(
      @PathVariable @ApiParam("Id of the categories") Long id) {
    categoryService.deleteCategory(id);
    return new ResponseEntity<>("Category was deleted successfully", HttpStatus.OK);
  }

  @ApiOperation(
      value =
          "Update categories replacing fields in the database with fields from the СategoryDto form."
              + " If categories doesnt exist, returns custom error message")
  @PutMapping("${url.category.update}")
  public CategoryDto updateItem(@RequestBody CategoryDto categoryDto) {
    return categoryService.updateCategory(categoryDto);
  }
}
