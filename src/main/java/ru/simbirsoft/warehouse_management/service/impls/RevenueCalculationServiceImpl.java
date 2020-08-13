package ru.simbirsoft.warehouse_management.service.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.simbirsoft.warehouse_management.exception.NotFoundException;
import ru.simbirsoft.warehouse_management.model.Order;
import ru.simbirsoft.warehouse_management.model.Shop;
import ru.simbirsoft.warehouse_management.repository.OrderRepository;
import ru.simbirsoft.warehouse_management.repository.ShopRepository;
import ru.simbirsoft.warehouse_management.service.RevenueCalculationService;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RevenueCalculationServiceImpl implements RevenueCalculationService {

  private final OrderRepository orderRepository;
  private final ShopRepository shopRepository;

  @Override
  public double calculateRevenueForOneShop(LocalDateTime start, LocalDateTime end, Long shopId) {
    if (!shopRepository.existsById(shopId)) {
      throw new NotFoundException(Shop.class, "id", String.valueOf(shopId));
    }
    List<Order> orders = orderRepository.findByOrderedAtBetweenAndShopIdAndIsConfirmedTrue(start, end, shopId);
    return orders.stream().mapToDouble(Order::getCost).sum();
  }

  @Override
  public Map<Shop, Double> calculateRevenueForAllShops(LocalDateTime start, LocalDateTime end) {
    Map<Shop, Double> shopRevenue = new LinkedHashMap<>();
    List<Shop> allShops = shopRepository.findAll();
    for (Shop shop : allShops) {
      shopRevenue.put(shop, calculateRevenueForOneShop(start, end, shop.getId()));
    }
    return shopRevenue;
  }

  @Override
  public double calculateAverageCheckForOneShop(
      LocalDateTime start, LocalDateTime end, Long shopId) {
    if (!shopRepository.existsById(shopId)) {
      throw new NotFoundException(Shop.class, "id", String.valueOf(shopId));
    }
    List<Order> orders = orderRepository.findByOrderedAtBetweenAndShopIdAndIsConfirmedTrue(start, end, shopId);
    return orders.stream().mapToDouble(Order::getCost).summaryStatistics().getAverage();
  }

  @Override
  public Map<Shop, Double> calculateAverageCheckForAllShops(
      LocalDateTime start, LocalDateTime end) {
    Map<Shop, Double> shopAverage = new LinkedHashMap<>();
    List<Shop> allShops = shopRepository.findAll();
    for (Shop shop : allShops) {
      shopAverage.put(shop, calculateAverageCheckForOneShop(start, end, shop.getId()));
    }
    return shopAverage;
  }
}
