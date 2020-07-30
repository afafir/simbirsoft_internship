package ru.simbirsoft.warehouse_management.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.warehouse_management.dto.SupplierDto;
import ru.simbirsoft.warehouse_management.dto.WarehouseDto;
import ru.simbirsoft.warehouse_management.dto.createForms.SupplierCreateDto;
import ru.simbirsoft.warehouse_management.dto.createForms.WarehouseCreateDto;
import ru.simbirsoft.warehouse_management.model.Supplier;
import ru.simbirsoft.warehouse_management.service.SupplierService;
import ru.simbirsoft.warehouse_management.service.WarehouseService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api("Supplier crud controller")
public class SupplierController {

  private final SupplierService supplierService;

  @ApiOperation(value = "returns all supplier from db")
  @GetMapping(value = "${url.supplier}")
  public List<SupplierDto> all() {
    return supplierService.getAll();
  }

  @ApiOperation(
      value =
          "returns supplier with such id.If supplier doesnt exist, returns custom error message ")
  @GetMapping("${url.supplier}/{id}")
  public SupplierDto getSupplier(@PathVariable @ApiParam("Id of the supplier") Long id) {
    return supplierService.getById(id);
  }


    @ApiOperation(
            value =
                    "returns supplier with such name.If supplier doesnt exist, returns custom error message ")
    @GetMapping("${url.supplier.name}/{name}")
    public SupplierDto getSupplierByName (@PathVariable @ApiParam("Name of the supplier") String name) {
        return supplierService.getByName(name);
    }

  @ApiOperation(value = "Creates new supplier from dto-form and returns Supplier entity with id")
  @PostMapping("${url.supplier.new}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public SupplierDto newSupplier(@RequestBody @Valid SupplierCreateDto supplierCreateDto) {
    return supplierService.createWarehouse(supplierCreateDto);
  }

  @ApiOperation(
      value =
          "Delete supplier from db with such id. If supplier doesnt exist, returns custom error message")
  @DeleteMapping("${url.supplier}/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<String> deleteWarehouse(
      @PathVariable @ApiParam("Id of the categories") Long id) {
      supplierService.deleteSupplier(id);
    return new ResponseEntity<>("Supplier was deleted successfully", HttpStatus.OK);
  }
}
