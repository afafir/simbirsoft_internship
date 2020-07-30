package ru.simbirsoft.warehouse_management.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.warehouse_management.dto.ItemDto;
import ru.simbirsoft.warehouse_management.dto.createForms.ItemCreateDto;
import ru.simbirsoft.warehouse_management.service.ItemService;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api("Operations pertaining to item information if warehouse")
public class ItemController {

  private final ItemService itemService;

  @ApiOperation(value = "returns all items from db")
  @GetMapping("${url.item}")
  public List<ItemDto> all() {
    return itemService.getAllItems();
  }

  @ApiOperation(
      value = "returns item with such id.If categories doesnt exist, returns custom error message ")
  @GetMapping("${url.item}/{id}")
  public ItemDto getItem(@PathVariable @ApiParam("Id of the item") Long id) {
    return itemService.getItem(id);
  }

  @ApiOperation(value = "Creates new Item from dto-form and returns Item entity with id")
  @PostMapping("${url.item.new}")
  public ItemDto newItem(@RequestBody @Valid ItemCreateDto itemCreateDto) {
    return itemService.createItem(itemCreateDto);
  }

  @ApiOperation(
      value =
          "Delete Item from db with such id. If categories doesnt exist, returns custom error message")
  @DeleteMapping("${url.item}/{id}")
  public ResponseEntity<String> deleteItem(@PathVariable @ApiParam("Id of the item") Long id) {
    itemService.deleteItem(id);
    return new ResponseEntity<>("Item was deleted successfully", HttpStatus.OK);
  }

  @ApiOperation(
      value =
          "Update item replacing fields in the database with fields from the ItemDto form."
              + " If item or new categories of this item doesnt exist in db, returns custom error message")
  @PutMapping("${url.item.update}")
  public ItemDto updateItem(@RequestBody ItemDto itemDto) {
    return itemService.updateItem(itemDto);
  }
}
