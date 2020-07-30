package ru.simbirsoft.warehouse_management.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.warehouse_management.dto.WarehouseDto;
import ru.simbirsoft.warehouse_management.dto.createForms.WarehouseCreateDto;
import ru.simbirsoft.warehouse_management.service.WarehouseService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api("Warehouse crud controller")
public class WarehouseController {

  private final WarehouseService warehouseService;

  @ApiOperation(value = "returns all warehouses from db")
  @GetMapping(value = "${url.warehouse}")
  public List<WarehouseDto> all() {
    return warehouseService.getAll();
  }

  @ApiOperation(
      value =
          "returns warehouse with such id.If warehouse doesnt exist, returns custom error message ")
  @GetMapping("${url.warehouse}/{id}")
  public WarehouseDto getWarehouse(@PathVariable @ApiParam("Id of the warehouse") Long id) {
    return warehouseService.getById(id);
  }

  @ApiOperation(value = "Creates new warehouse from dto-form and returns supplier entity with id")
  @PostMapping("${url.warehouse.new}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public WarehouseDto newWarehouse(@RequestBody @Valid WarehouseCreateDto warehouseCreateDto) {
    return warehouseService.createWarehouse(warehouseCreateDto);
  }

  @ApiOperation(
      value =
          "Delete warehouse from db with such id. If warehouse doesnt exist, returns custom error message")
  @DeleteMapping("${url.warehouse}/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<String> deleteWarehouse(
      @PathVariable @ApiParam("Id of the warehouse") Long id) {
    warehouseService.deleteWarehouse(id);
    return new ResponseEntity<>("Warehouse was deleted successfully", HttpStatus.OK);
  }
}
