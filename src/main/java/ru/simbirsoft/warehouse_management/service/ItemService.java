package ru.simbirsoft.warehouse_management.service;

import ru.simbirsoft.warehouse_management.dto.ItemDto;
import ru.simbirsoft.warehouse_management.dto.createForms.ItemCreateDto;
import ru.simbirsoft.warehouse_management.model.Item;

import java.util.List;

public interface ItemService {
  /**
   * saves new Item into db from creating form
   *
   * @param itemCreateDto - forrm with item fields
   * @return created item with id
   */
  ItemDto createItem(ItemCreateDto itemCreateDto);
  /**
   * Delete Item entity from db by this id
   *
   * @param id - id of entity in db
   * @throws ru.simbirsoft.warehouse_management.exception.NotFoundException if entity with such id
   *     doesnt exist
   */
  void deleteItem(Long id);
  /**
   * returns all items from db
   *
   * @return List of all items
   */
  List<ItemDto> getAllItems();
  /**
   * returns Item entity from db by this id
   *
   * @param id - id of entity in db
   * @return item entity
   * @throws ru.simbirsoft.warehouse_management.exception.NotFoundException if entity with such id
   *     doesnt exist
   */
  ItemDto getItem(Long id);
  /**
   * Update entity from db by id from itemDto. This dto also contains all fields of the Item. These
   * fields will replace the fields of an entity
   *
   * @param itemDto - DTO which contains all changes of the entity
   * @return updated categories entity
   */
  ItemDto updateItem(ItemDto itemDto);
}
