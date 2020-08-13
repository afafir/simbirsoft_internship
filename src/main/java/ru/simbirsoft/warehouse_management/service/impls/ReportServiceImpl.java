package ru.simbirsoft.warehouse_management.service.impls;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import liquibase.pro.packaged.D;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.simbirsoft.warehouse_management.dto.mapper.ShopMapper;
import ru.simbirsoft.warehouse_management.dto.mapper.WarehouseMapper;
import ru.simbirsoft.warehouse_management.model.Item;
import ru.simbirsoft.warehouse_management.model.Shop;
import ru.simbirsoft.warehouse_management.model.Warehouse;
import ru.simbirsoft.warehouse_management.repository.ShopRepository;
import ru.simbirsoft.warehouse_management.service.ReportService;
import ru.simbirsoft.warehouse_management.service.ItemTransactionCounterService;
import ru.simbirsoft.warehouse_management.service.ShopService;
import ru.simbirsoft.warehouse_management.service.WarehouseService;
import ru.simbirsoft.warehouse_management.util.PdfReportUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

  private final PdfReportUtil pdfReportUtil;

  private final ItemTransactionCounterService salesCounterService;
  private final RevenueCalculationServiceImpl revenueCalculationService;
  private final ShopService shopService;
  private final WarehouseService warehouseService;

  private final ShopMapper shopMapper;
  private final WarehouseMapper warehouseMapper;

  @Override
  public ByteArrayInputStream allShopsOrderReport(LocalDateTime start, LocalDateTime end) {
    Map<Shop, Map<Item, Integer>> countOfSaledItemsForEachShop =
        salesCounterService.getCountOfSaledItemsForAllShop(start, end);
    Document document = new Document();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      PdfWriter.getInstance(document, out);
      document.open();
      pdfReportUtil.generateSalesReportForShops(countOfSaledItemsForEachShop, document);
      document.close();
    } catch (DocumentException e) {
      throw new IllegalStateException("Can not generate pdf document");
    }
    return new ByteArrayInputStream(out.toByteArray());
  }

  @Override
  public ByteArrayInputStream oneShopOrderReport(
      Long shopId, LocalDateTime start, LocalDateTime end) {
    Map<Item, Integer> countOfSaledItemsForOneShop =
        salesCounterService.getCountOfSaledItemsForOneShop(start, end, shopId);
    Document document = new Document();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      PdfWriter.getInstance(document, out);
      document.open();
      pdfReportUtil.generateSalesReportForOneShop(
          shopMapper.map(shopService.getById(shopId)), countOfSaledItemsForOneShop, document);
      document.close();
    } catch (DocumentException e) {
      throw new IllegalStateException("Can not generate pdf document");
    }
    return new ByteArrayInputStream(out.toByteArray());
  }

  @Override
  public ByteArrayInputStream allWarehousesWriteoffReport(LocalDateTime start, LocalDateTime end) {
    Map<Warehouse, Map<Item, Integer>> countOfWriteoffedItemsForEachWarehouse =
        salesCounterService.getCountOfWriteoffedItemsForAllWarehouses(start, end);
    Document document = new Document();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      PdfWriter.getInstance(document, out);
      document.open();
      pdfReportUtil.generateWriteoffReportForWarehouses(
          countOfWriteoffedItemsForEachWarehouse, document);
      document.close();
    } catch (DocumentException e) {
      throw new IllegalStateException("Can not generate pdf document");
    }
    return new ByteArrayInputStream(out.toByteArray());
  }

  @Override
  public ByteArrayInputStream oneWarehouseWriteoffReport(
      Long warehouseId, LocalDateTime start, LocalDateTime end) {
    Map<Item, Integer> countOfWriteoffedItemsForOneWarehouse =
        salesCounterService.getCountOfWriteoffedItemsForOneWarehouses(start, end, warehouseId);
    Document document = new Document();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      PdfWriter.getInstance(document, out);
      document.open();
      pdfReportUtil.generateWriteoffReportForOneWarehouse(
          warehouseMapper.map(warehouseService.getById(warehouseId)),
          countOfWriteoffedItemsForOneWarehouse,
          document);
      document.close();
    } catch (DocumentException e) {
      throw new IllegalStateException("Can not generate pdf document");
    }
    return new ByteArrayInputStream(out.toByteArray());
  }

  @Override
  public ByteArrayInputStream allShopsRevenueReport(LocalDateTime start, LocalDateTime end) {
    Map<Shop, Double> allShosRevenue =
        revenueCalculationService.calculateRevenueForAllShops(start, end);
    Document document = new Document();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      PdfWriter.getInstance(document, out);
      document.open();
      pdfReportUtil.generateRevenueReportForShops(allShosRevenue, document);
      document.close();
    } catch (DocumentException e) {
      throw new IllegalStateException("Can not generate pdf document");
    }
    return new ByteArrayInputStream(out.toByteArray());
  }

  @Override
  public ByteArrayInputStream oneShopRevenueReport(
      Long shopId, LocalDateTime start, LocalDateTime end) {
    Double shopRevenue = revenueCalculationService.calculateRevenueForOneShop(start, end, shopId);
    Document document = new Document();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      PdfWriter.getInstance(document, out);
      document.open();
      pdfReportUtil.generateRevenueReportForOneShop(
          shopMapper.map(shopService.getById(shopId)), shopRevenue, document);
      document.close();
    } catch (DocumentException e) {
      throw new IllegalStateException("Can not generate pdf document");
    }
    return new ByteArrayInputStream(out.toByteArray());
  }

  @Override
  public ByteArrayInputStream allShopsAverageCheckReport(LocalDateTime start, LocalDateTime end) {
    Map<Shop, Double> allShopAverageCheck =
        revenueCalculationService.calculateAverageCheckForAllShops(start, end);
    Document document = new Document();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      PdfWriter.getInstance(document, out);
      document.open();
      pdfReportUtil.generateAverageCheckForShops(allShopAverageCheck, document);
      document.close();
    } catch (DocumentException e) {
      throw new IllegalStateException("Can not generate pdf document");
    }
    return new ByteArrayInputStream(out.toByteArray());
  }

  @Override
  public ByteArrayInputStream oneShopAverageCheckReport(
      Long shopId, LocalDateTime start, LocalDateTime end) {
    Double shopAverageCheck =
        revenueCalculationService.calculateAverageCheckForOneShop(start, end, shopId);
    Document document = new Document();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      PdfWriter.getInstance(document, out);
      document.open();
      pdfReportUtil.generateAverageCheckForShop(
          shopMapper.map(shopService.getById(shopId)), shopAverageCheck, document);
      document.close();
    } catch (DocumentException e) {
      throw new IllegalStateException("Can not generate pdf document");
    }
    return new ByteArrayInputStream(out.toByteArray());
  }
}
