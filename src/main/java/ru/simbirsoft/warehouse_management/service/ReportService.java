package ru.simbirsoft.warehouse_management.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;

public interface ReportService {

  /**
   * Generate pdf report about sales of all shops
   *
   * @param start - Time from which data is taken
   * @param end - Time until which data is taken
   * @return ByteArrayInput stream of pdf document
   */
  ByteArrayInputStream allShopsOrderReport(LocalDateTime start, LocalDateTime end);

  /**
   * Generate pdf report about sales of one shop
   *
   * @param shopId - shop id that the report generate for
   * @param start - Time from which data is taken
   * @param end - Time until which data is taken
   * @return ByteArrayInput stream of pdf document
   */
  ByteArrayInputStream oneShopOrderReport(Long shopId, LocalDateTime start, LocalDateTime end);

  /**
   * Generate pdf report about writeoffs of all warehouses
   *
   * @param start - Time from which data is taken
   * @param end - Time until which data is taken
   * @return ByteArrayInput stream of pdf document
   */
  ByteArrayInputStream allWarehousesWriteoffReport(LocalDateTime start, LocalDateTime end);


  /**
   * Generate pdf report about writeoffs of one warehouse
   *
   * @param warehouseId - warehouse id that the report generate for
   * @param start - Time from which data is taken
   * @param end - Time until which data is taken
   * @return ByteArrayInput stream of pdf document
   */
  ByteArrayInputStream oneWarehouseWriteoffReport(
      Long warehouseId, LocalDateTime start, LocalDateTime end);


  /**
   * Generate pdf report about revenue of all shops
   *
   * @param start - Time from which data is taken
   * @param end - Time until which data is taken
   * @return ByteArrayInput stream of pdf document
   */
  ByteArrayInputStream allShopsRevenueReport(LocalDateTime start, LocalDateTime end);

  /**
   * Generate pdf report about revenue of one shop
   *
   * @param shopId - shop id that the report generate for
   * @param start - Time from which data is taken
   * @param end - Time until which data is taken
   * @return ByteArrayInput stream of pdf document
   */
  ByteArrayInputStream oneShopRevenueReport(Long shopId, LocalDateTime start, LocalDateTime end);


  /**
   * Generate pdf report about average checks of all shops
   *
   * @param start - Time from which data is taken
   * @param end - Time until which data is taken
   * @return ByteArrayInput stream of pdf document
   */
  ByteArrayInputStream allShopsAverageCheckReport(LocalDateTime start, LocalDateTime end);

  /**
   * Generate pdf report about average check of one shop
   *
   * @param shopId - shop id that the report generate for
   * @param start - Time from which data is taken
   * @param end - Time until which data is taken
   * @return ByteArrayInput stream of pdf document
   */
  ByteArrayInputStream oneShopAverageCheckReport(Long shopId, LocalDateTime start, LocalDateTime end);
}
