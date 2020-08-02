package ru.simbirsoft.warehouse_management.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.warehouse_management.dto.ShopDto;
import ru.simbirsoft.warehouse_management.dto.createForms.ShopCreateDto;
import ru.simbirsoft.warehouse_management.service.ShopService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api("Crud controller for shop")
public class ShopController {

  private final ShopService shopService;

  @ApiOperation(value = "returns all shops from db")
  @GetMapping(value = "${url.shop}")
  public List<ShopDto> all() {
    return shopService.getAll();
  }

  @ApiOperation(
      value = "returns shop with such id.If shop doesnt exist, returns custom error message ")
  @GetMapping("${url.shop}/{id}")
  public ShopDto getShop(@PathVariable @ApiParam("Id of the shop") Long id) {
    return shopService.getById(id);
  }

  @ApiOperation(value = "Creates new shop from dto-form and returns shop entity with id")
  @PostMapping("${url.shop.new}")
  public ShopDto newShop(@RequestBody @Valid ShopCreateDto shopCreateDto) {
    return shopService.createShop(shopCreateDto);
  }

  @ApiOperation(
      value =
          "Delete Shop from db with such id. If shop doesnt exist, returns custom error message")
  @DeleteMapping("${url.shop}/{id}")
  public ResponseEntity<String> deleteWarehouse(@PathVariable @ApiParam("Id of the shop") Long id) {
    shopService.deleteById(id);
    return new ResponseEntity<>("Shop was deleted successfully", HttpStatus.OK);
  }

  @ApiOperation(
      value =
          "returns shop with such name.If shop doesnt exist, returns custom error message ")
  @GetMapping("${url.shop.name}/{name}")
  public ShopDto getShopByName(
      @PathVariable @ApiParam("Name of the shop") String name) {
    return shopService.getByName(name);
  }
}
