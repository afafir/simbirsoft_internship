package ru.simbirsoft.warehouse_management.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.warehouse_management.dto.ItemDto;
import ru.simbirsoft.warehouse_management.dto.createForms.ItemCreateDto;
import ru.simbirsoft.warehouse_management.model.Item;
import ru.simbirsoft.warehouse_management.service.ItemService;

import java.util.List;

@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "returns all items from db")
    @GetMapping("/items")
    private List<ItemDto> all(){
        return ItemDto.from(itemService.getAllItems());
    }

    @ApiOperation(value = "returns item with such id.If category doesnt exist, returns custom error message ")
    @GetMapping("/items/{id}")
    private ItemDto getItem(@PathVariable Long id){
        return ItemDto.from(itemService.getItem(id));
    }

    @ApiOperation(value = "Creates new Item from dto-form and returns Item entity with id")
    @PostMapping("/items/new")
    private Item newItem(@RequestBody ItemCreateDto itemCreateDto){
        return itemService.createItem(itemCreateDto);
    }

    @ApiOperation(value = "Delete Item from db with such id. If category doesnt exist, returns custom error message")
    @DeleteMapping("/items/{id}")
    private ResponseEntity<String> deleteItem(@PathVariable Long id){
        itemService.deleteItem(id);
        return new ResponseEntity<>("Item was deleted successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Update item replacing fields in the database with fields from the ItemDto form." +
            " If item or new category of this item doesnt exist in db, returns custom error message")
    @PutMapping("/items/update")
    private ItemDto updateItem(@RequestBody ItemDto itemDto){
        return ItemDto.from(itemService.updateItem(itemDto));
    }
}
