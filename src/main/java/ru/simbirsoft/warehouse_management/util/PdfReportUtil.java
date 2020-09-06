package ru.simbirsoft.warehouse_management.util;

import com.itextpdf.text.Document;
import ru.simbirsoft.warehouse_management.model.Item;
import ru.simbirsoft.warehouse_management.model.Shop;
import ru.simbirsoft.warehouse_management.model.Warehouse;

import javax.print.Doc;
import java.util.Map;

public interface PdfReportUtil {

  /**
   * Fills the pdf document for all shops sales report
   *
   * @param itemCountsForShops - info about sales of each shop. Key is shop, Value is a map that
   *     contatins info about item and counts of sale for this item
   * @param document - pdf document that should be filled
   */
  void generateSalesReportForShops(
      Map<Shop, Map<Item, Integer>> itemCountsForShops, Document document);

  /**
   * Fills the pdf document for one shop sales report
   *
   * @param shop - shop entity that the report generated for
   * @param itemCounts - map about sales in this shop. Key is Item, value is counts of sales of this item
   * @param document - pdf document that should be filled
   */
  void generateSalesReportForOneShop(Shop shop, Map<Item, Integer> itemCounts, Document document);

  /**
   * Fills the pdf document for all warehouses writeoffs
   *
   * @param itemCountsForWarehouse - info about writeoffs of each warehouse. Key is warehouse, Value is a map that
   *     contatins info about item and counts of writeoffs for this item
   * @param document - pdf document that should be filled
   */
  void generateWriteoffReportForWarehouses(
      Map<Warehouse, Map<Item, Integer>> itemCountsForWarehouse, Document document);

  /**
   * Fills the pdf document for one warehouse writeoffs
   *
   * @param warehouse - warehouse entity that the report is generated for
   * @param itemCounts - map key of which is Item entity, value is counts of writeoffs for this item
   * @param document - pdf document that should be filled
   */
  void generateWriteoffReportForOneWarehouse(
      Warehouse warehouse, Map<Item, Integer> itemCounts, Document document);

  /**
   * Fills the pdf document for all shops revenue
   *
   * @param shopRevenue - map key of which is Shop entity, value is revenue of this shop
   * @param document - pdf document that should be filled
   */
  void generateRevenueReportForShops(Map<Shop, Double> shopRevenue, Document document);

  /**
   * Fills the pdf document for all shops revenue
   *
   * @param shop - map key of which is Shop entity, value is revenue of this shop
   * @param document - pdf document that should be filled
   */
  void generateRevenueReportForOneShop(Shop shop, Double revenue, Document document);

  /**
   * Fills the pdf document for all shops average checks
   *
   * @param shopAverages - map key of which is Shop entity, value is averages of this shop
   * @param document - pdf document that should be filled
   */
  void generateAverageCheckForShops(Map<Shop, Double> shopAverages, Document document);

  /**
   * Fills the pdf document for one shop average check
   *
   * @param shop - map key of which is Shop entity, value is average check of this shop
   * @param document - pdf document that should be filled
   */
  void generateAverageCheckForShop(Shop shop, Double average, Document document);
}
