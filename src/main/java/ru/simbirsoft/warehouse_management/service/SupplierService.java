package ru.simbirsoft.warehouse_management.service;



import ru.simbirsoft.warehouse_management.dto.SupplierDto;
import ru.simbirsoft.warehouse_management.dto.createForms.SupplierCreateDto;

import java.util.List;

public interface SupplierService {
    /**
     * saves new Supplier into db from creating form
     *
     * @param supplierCreateDto - forrm with supplier fields
     * @return created warehouse with id
     */
    SupplierDto createWarehouse(SupplierCreateDto supplierCreateDto);
    /**
     * Delete Supplier entity from db by this id
     *
     * @param id - id of entity in db
     * @throws ru.simbirsoft.warehouse_management.exception.NotFoundException if entity with such id
     *     doesnt exist
     */
    void deleteSupplier(Long id);
    /**
     * returns all supplier from db
     *
     * @return List of all suppliers
     */
    List<SupplierDto> getAll();
    /**
     * returns Supplier entity from db by this id
     *
     * @param id - id of entity in db
     * @return supplier entity
     * @throws ru.simbirsoft.warehouse_management.exception.NotFoundException if entity with such id
     *     doesnt exist
     */
    SupplierDto getById(Long id);

    /**
     * returns Supplier entity from db by this name
     *
     * @param name - name of entity in db
     * @return supplier entity
     * @throws ru.simbirsoft.warehouse_management.exception.NotFoundException if entity with such name
     *     doesnt exist
     */
    SupplierDto getByName(String name);


}
