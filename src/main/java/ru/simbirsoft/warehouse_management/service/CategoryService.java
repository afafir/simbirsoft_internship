package ru.simbirsoft.warehouse_management.service;

import ru.simbirsoft.warehouse_management.dto.CategoryDto;
import ru.simbirsoft.warehouse_management.dto.createForms.CategoryCreateDto;
import ru.simbirsoft.warehouse_management.model.Category;

import java.util.List;


public interface CategoryService {
    /**
     * Method saves new Category into db from creating form
     * @param categoryCreateDto - forrm with desc and name string fields
     * @return created category with id
     */
    Category createCategory(CategoryCreateDto categoryCreateDto);

    /**
     * Method returns all categories from db
     * @return List of all categories
     */
    List<Category> getAllCategories();

    /**
     * Return Category entity from db by this id
     * @param id - id of entity in db
     * @return category entiity
     * @throws ru.simbirsoft.warehouse_management.exception.NotFoundException if entity with such id doesnt exist
     */
    Category getCategory(Long id);
    /**
     * Delete entity from db by this id
     * @param id - id of entity in db
     * @throws ru.simbirsoft.warehouse_management.exception.NotFoundException if entity with such id doesnt exist
     */
    void deleteCategory(Long id);

    /**
     * Update entity from db by id from categoryDto. This dto also contains all fields of the Category.
     * These fields will replace the fields of an entity
     * @param categoryDto -  DTO which contains all changes of the entity
     * @return updated category entity
     */
    Category updateCategory(CategoryDto categoryDto);


 }
