package ru.simbirsoft.warehouse_management.service;

import ru.simbirsoft.warehouse_management.model.Shop;

import java.time.LocalDateTime;
import java.util.Map;

public interface RevenueCalculationService {

    /**
     * Calculate revenue in specified time period for shop with this id
     *
     * @param start - Time from which data is taken
     * @param end - Time until which data is taken
     * @param shopId - id of the shop that the revenue is being generated for
     * @return double value of revenue for this shop
     */
    double calculateRevenueForOneShop(LocalDateTime start, LocalDateTime end, Long shopId);

    /**
     * Calculates revenue for all shops
     *
     * @param start - Time from which data is taken
     * @param end - Time until which data is taken
     * @return Map whose key is Shop, value is shops revenue
     */
    Map<Shop, Double> calculateRevenueForAllShops(LocalDateTime start, LocalDateTime end);

    /**
     * Calculate revenue in specified time period for shop with this id
     *
     * @param start - Time from which data is taken
     * @param end - Time until which data is taken
     * @param shopId - id of the shop that the averahe check is being generated for
     * @return double value of average check for this shop
     */
    double calculateAverageCheckForOneShop(LocalDateTime start, LocalDateTime end, Long shopId);

    /**
     * Calculates averagge check for all shops
     *
     * @param start - Time from which data is taken
     * @param end - Time until which data is taken
     * @return Map whose key is Shop, value is shops average  check
     */
    Map<Shop, Double> calculateAverageCheckForAllShops(LocalDateTime start, LocalDateTime end);



}
