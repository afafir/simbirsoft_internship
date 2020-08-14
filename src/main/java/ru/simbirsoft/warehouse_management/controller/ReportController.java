package ru.simbirsoft.warehouse_management.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.warehouse_management.service.ReportService;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Api("report controller")
public class ReportController {

  private final ReportService reportService;

  @ApiOperation(value = "Creates pdf report about sales in all shops")
  @GetMapping(value = "${url.report.sales}", produces = MediaType.APPLICATION_PDF_VALUE)
  @PreAuthorize("hasAuthority('WAREHOUSE_KEEPER')")
  public ResponseEntity<InputStreamResource> shopsReport(
      @RequestParam(required = true, value = "start time")
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime start,
      @RequestParam(required = true, value = "end time")
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime end) {

    ByteArrayInputStream bis = reportService.getAllShopsOrderReport(start, end);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition", "attachment; filename=orders.pdf");

    return ResponseEntity.ok()
        .headers(headers)
        .contentType(MediaType.APPLICATION_PDF)
        .body(new InputStreamResource(bis));
  }

  @ApiOperation(value = "Creates pdf report about sales in shop with such id")
  @GetMapping(value = "${url.report.sales}/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
  @PreAuthorize("hasAuthority('WAREHOUSE_KEEPER')")
  public ResponseEntity<InputStreamResource> shopReportById(
      @RequestParam(required = true, value = "start time")
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime start,
      @RequestParam(required = true, value = "end time")
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime end,
      @PathVariable @ApiParam("shop id") Long id) {

    ByteArrayInputStream bis = reportService.getOneShopOrderReport(id, start, end);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition", "attachment; filename=order.pdf");

    return ResponseEntity.ok()
        .headers(headers)
        .contentType(MediaType.APPLICATION_PDF)
        .body(new InputStreamResource(bis));
  }

  @ApiOperation(value = "Creates pdf report about writeoffs in all warehouses")
  @GetMapping(value = "${url.report.writeoff}", produces = MediaType.APPLICATION_PDF_VALUE)
  @PreAuthorize("hasAuthority('WAREHOUSE_KEEPER')")
  public ResponseEntity<InputStreamResource> writeoffReportAllWarehouses(
      @RequestParam(required = true, value = "start time")
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime start,
      @RequestParam(required = true, value = "end time")
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime end) {

    ByteArrayInputStream bis = reportService.getAllWarehousesWriteoffReport(start, end);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition", "attachment; filename=writeoffs.pdf");

    return ResponseEntity.ok()
        .headers(headers)
        .contentType(MediaType.APPLICATION_PDF)
        .body(new InputStreamResource(bis));
  }

  @ApiOperation(value = "Creates pdf report about writeoffs in one warehouse with such id")
  @GetMapping(value = "${url.report.writeoff}/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
  @PreAuthorize("hasAuthority('WAREHOUSE_KEEPER')")
  public ResponseEntity<InputStreamResource> writeoffReportByWarehouseId(
      @RequestParam(required = true, value = "start time")
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime start,
      @RequestParam(required = true, value = "end time")
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime end,
      @PathVariable @ApiParam("Warehouse id") Long id) {

    ByteArrayInputStream bis = reportService.getOneWarehouseWriteoffReport(id, start, end);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition", "attachment; filename=writeoff.pdf");

    return ResponseEntity.ok()
        .headers(headers)
        .contentType(MediaType.APPLICATION_PDF)
        .body(new InputStreamResource(bis));
  }

  @ApiOperation(value = "Creates pdf report about revenue in all shops")
  @GetMapping(value = "${url.report.revenue}", produces = MediaType.APPLICATION_PDF_VALUE)
  @PreAuthorize("hasAuthority('WAREHOUSE_KEEPER')")
  public ResponseEntity<InputStreamResource> allShopsRevenueReport(
      @RequestParam(required = true, value = "start time")
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime start,
      @RequestParam(required = true, value = "end time")
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime end) {

    ByteArrayInputStream bis = reportService.getAllShopsRevenueReport(start, end);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition", "attachment; filename=revenues.pdf");

    return ResponseEntity.ok()
        .headers(headers)
        .contentType(MediaType.APPLICATION_PDF)
        .body(new InputStreamResource(bis));
  }

  @ApiOperation(value = "Creates pdf report about revenue in one shop with such id")
  @GetMapping(value = "${url.report.revenue}/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
  @PreAuthorize("hasAuthority('WAREHOUSE_KEEPER')")
  public ResponseEntity<InputStreamResource> shopRevenueReport(
      @RequestParam(required = true, value = "start time")
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime start,
      @RequestParam(required = true, value = "end time")
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime end,
      @PathVariable @ApiParam("Shop id") Long id) {

    ByteArrayInputStream bis = reportService.getOneShopRevenueReport(id, start, end);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition", "attachment; filename=revenue.pdf");

    return ResponseEntity.ok()
        .headers(headers)
        .contentType(MediaType.APPLICATION_PDF)
        .body(new InputStreamResource(bis));
  }

  @ApiOperation(value = "Creates pdf report about averagechecks in all shops")
  @GetMapping(value = "${url.report.average}", produces = MediaType.APPLICATION_PDF_VALUE)
  @PreAuthorize("hasAuthority('WAREHOUSE_KEEPER')")
  public ResponseEntity<InputStreamResource> allShopAverageCheckReport(
      @RequestParam(required = true, value = "start time")
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime start,
      @RequestParam(required = true, value = "end time")
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime end) {

    ByteArrayInputStream bis = reportService.getAllShopsAverageCheckReport(start, end);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition", "attachment; filename=averageChecks.pdf");

    return ResponseEntity.ok()
        .headers(headers)
        .contentType(MediaType.APPLICATION_PDF)
        .body(new InputStreamResource(bis));
  }

  @ApiOperation(value = "Creates pdf report about average check in one shop with such id")
  @GetMapping(value = "${url.report.average}/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
  @PreAuthorize("hasAuthority('WAREHOUSE_KEEPER')")
  public ResponseEntity<InputStreamResource> oneShopAverageCheckReport(
      @RequestParam(required = true, value = "start time")
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime start,
      @RequestParam(required = true, value = "end time")
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime end,
      @PathVariable @ApiParam("Shop id") Long id) {

    ByteArrayInputStream bis = reportService.getOneShopAverageCheckReport(id, start, end);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition", "attachment; filename=averageCheck.pdf");

    return ResponseEntity.ok()
        .headers(headers)
        .contentType(MediaType.APPLICATION_PDF)
        .body(new InputStreamResource(bis));
  }
}
