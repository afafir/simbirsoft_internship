package ru.simbirsoft.warehouse_management.service;

import ru.simbirsoft.warehouse_management.dto.WarehouseDto;
import ru.simbirsoft.warehouse_management.dto.createForms.WarehouseCreateDto;
import ru.simbirsoft.warehouse_management.model.ItemWarehouse;

import java.util.List;

public interface WarehouseService {
    /**
     * saves new Warehouse into db from creating form
     *
     * @param warehouseCreateDto - forrm with warehouse fields
     * @return created warehouse with id
     */
    WarehouseDto createWarehouse(WarehouseCreateDto warehouseCreateDto);
    /**
     * Delete Warehouse entity from db by this id
     *
     * @param id - id of entity in db
     * @throws ru.simbirsoft.warehouse_management.exception.NotFoundException if entity with such id
     *     doesnt exist
     */
    void deleteWarehouse(Long id);
    /**
     * returns all warehouses from db
     *
     * @return List of all warehouses
     */
    List<WarehouseDto> getAll();
    /**
     * returns Warehouse entity from db by this id
     *
     * @param id - id of entity in db
     * @return warehouse entity
     * @throws ru.simbirsoft.warehouse_management.exception.NotFoundException if entity with such id
     *     doesnt exist
     */
    WarehouseDto getById(Long id);


    /**
     * Put item (and its count) into warehouse
     *
     * @param itemWarehouse - contains item to put and warehouse which stores it
     */
    void putItem(ItemWarehouse itemWarehouse);

    /**
     * Put list of items into warehouse
     *
     * @param itemWarehouses - list which contains info about each item and its count and warehouse which stores it
     */
    void putAllItems(List<ItemWarehouse> itemWarehouses);
}
