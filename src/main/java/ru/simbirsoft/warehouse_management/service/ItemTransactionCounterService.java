package ru.simbirsoft.warehouse_management.service;

import ru.simbirsoft.warehouse_management.model.Item;
import ru.simbirsoft.warehouse_management.model.Shop;
import ru.simbirsoft.warehouse_management.model.Warehouse;

import java.time.LocalDateTime;
import java.util.Map;

public interface ItemTransactionCounterService {

  /**
   * Generates a map whose key is Item, and value is the number of sales for the shop with this id
   * in this time period
   *
   * @param start - Time from which data is taken
   * @param end - Time until which data is taken
   * @param shopId - id of the shop that the report is being generated for
   * @return returns a map whose key is item, and the value is the number of sales
   */
  Map<Item, Integer> getCountOfSaledItemsForOneShop(
      LocalDateTime start, LocalDateTime end, Long shopId);

  /**
   * Generates a Map containing sales information for each store in a specified time period
   *
   * @param start - Time from which data is taken
   * @param end - Time until which data is taken
   * @return Returns a map whose key is the Shop, and the value is the sales information for that
   *     shop. Value is a Map whose key is item, and value is the number of sales for this item
   */
  Map<Shop, Map<Item, Integer>> getCountOfSaledItemsForAllShop(
      LocalDateTime start, LocalDateTime end);

  /**
   * @param start - Time from which data is taken
   * @param end - Time until which data is taken
   * @return Returns a map whose key is the Shop, and the value is the sales information for that *
   *     shop. Value is a Map whose key is item, and value is the number of sales for this item
   */
  Map<Warehouse, Map<Item, Integer>> getCountOfWriteoffedItemsForAllWarehouses(
      LocalDateTime start, LocalDateTime end);

  /**
   * * Generates a Map containing writeoff information for warehouse in a specified time period
   *
   * @param start - Time from which data is taken
   * @param end - Time until which data is taken
   * @param warehouseId - id of the warehouse that the report is being generated for
   * @return Map key which is writeoffed item, value is quantity of writeoffed items
   */
  Map<Item, Integer> getCountOfWriteoffedItemsForOneWarehouses(
      LocalDateTime start, LocalDateTime end, Long warehouseId);
}
