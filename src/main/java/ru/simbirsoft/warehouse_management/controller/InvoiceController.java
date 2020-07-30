package ru.simbirsoft.warehouse_management.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.warehouse_management.dto.InvoiceDto;
import ru.simbirsoft.warehouse_management.dto.createForms.InvoiceCreateDto;
import ru.simbirsoft.warehouse_management.service.InvoiceService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api("Invoice controller (creating, deleting, confirming)")
public class InvoiceController {

  private final InvoiceService invoiceService;

  @ApiOperation(value = "Create new invoice. Returns dto of created invoice ")
  @PostMapping("${url.invoice.new}")
  public InvoiceDto createInvoice(@RequestBody @Valid InvoiceCreateDto invoiceCreateDto) {
    return invoiceService.createInvoice(invoiceCreateDto);
  }

  @ApiOperation(value = "Get invoice by id")
  @GetMapping("${url.invoice}/{id}")
  public InvoiceDto createInvoice(@ApiParam("Id of the invoice") @PathVariable Long id) {
    return invoiceService.getInvoiceById(id);
  }

  @ApiOperation(value = "Get invoice by id")
  @DeleteMapping("${url.invoice}/{id}")
  public ResponseEntity<String> deleteInvoice(@ApiParam("Id of the invoice") @PathVariable Long id) {
    invoiceService.deleteById(id);
    return ResponseEntity.ok("Invoice was deleted successfully");
  }

  @ApiOperation(value = "Confirm invoice by id")
  @PostMapping("${url.invoice.confirm}/{id}")
  public ResponseEntity<String> confirmInvoice(@ApiParam("id of the invoice to confirm") @PathVariable Long id){
    invoiceService.confirmInvoice(id);
    return ResponseEntity.ok("Invoice confirmed. Warehouse is replenished");
  }

  @ApiOperation(value = "Get all not confirmed invoices")
  @GetMapping("${url.invoice.notConfirmed}")
  public List<InvoiceDto> getNotConfirmed(){
    return invoiceService.getNotConfirmed();
  }

}
