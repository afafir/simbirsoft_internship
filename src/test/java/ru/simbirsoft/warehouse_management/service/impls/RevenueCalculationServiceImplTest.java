package ru.simbirsoft.warehouse_management.service.impls;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.simbirsoft.warehouse_management.exception.NotFoundException;
import ru.simbirsoft.warehouse_management.model.*;
import ru.simbirsoft.warehouse_management.model.pk.ItemWriteoffPk;
import ru.simbirsoft.warehouse_management.model.pk.OrderItemPk;
import ru.simbirsoft.warehouse_management.repository.OrderRepository;
import ru.simbirsoft.warehouse_management.repository.ShopRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RevenueCalculationServiceImplTest {

  @InjectMocks @Spy private RevenueCalculationServiceImpl revenueCalculationService;

  @Mock private OrderRepository orderRepository;
  @Mock private ShopRepository shopRepository;

  private Order order;
  private Order order1;
  private Order order2;
  private Shop shop1;
  private Shop shop2;
  private Item item;
  private Item item1;
  private LocalDateTime start;
  private LocalDateTime end;

  @BeforeEach
  private void before() {
    start = LocalDateTime.now().minusWeeks(1);
    end = LocalDateTime.now();

    item = Item.builder().name("item").price(1.0F).description("desc").build();
    item1 = Item.builder().name("item1").price(2.0F).description("desc1").build();

    shop1 = Shop.builder().id(1L).name("shop1").build();
    shop2 = Shop.builder().id(2L).name("shop2").build();

    order = Order.builder().shop(shop1).isConfirmed(true).build();
    order1 = Order.builder().shop(shop1).isConfirmed(true).build();
    order2 = Order.builder().shop(shop2).isConfirmed(true).build();

    OrderItem orderItem = new OrderItem(new OrderItemPk(1L, 1L), item, order, 2);
    OrderItem orderItem1 = new OrderItem(new OrderItemPk(2L, 1L), item1, order, 5);
    OrderItem orderItem2 = new OrderItem(new OrderItemPk(1L, 2L), item, order1, 4);
    OrderItem orderItem3 = new OrderItem(new OrderItemPk(2L, 3L), item1, order2, 3);

    order.setItems(Arrays.asList(orderItem, orderItem1));
    order.setCost(
        orderItem.getCount() * orderItem.getItem().getPrice()
            + orderItem1.getCount() * orderItem1.getItem().getPrice());
    order1.setItems(Collections.singletonList(orderItem2));
    order1.setCost(orderItem2.getCount() * orderItem2.getItem().getPrice());
    order2.setItems(Collections.singletonList(orderItem3));
    order2.setCost(orderItem3.getCount() * orderItem3.getItem().getPrice());
  }

  @Test
  void calculateRevenueForOneShop() {
    Mockito.doReturn(true).when(shopRepository).existsById(1L);
    Mockito.doReturn(Arrays.asList(order, order1))
        .when(orderRepository)
        .findByOrderedAtBetweenAndShopIdAndIsConfirmedTrue(start, end, 1L);
    double revenueForOneShop = revenueCalculationService.calculateRevenueForOneShop(start, end, 1L);
    assertEquals(revenueForOneShop, 16);
  }

  @Test
  void calculateRevenueForOneShopThatDoesntExist() {
    Mockito.doReturn(false).when(shopRepository).existsById(1L);
    assertThrows(
        NotFoundException.class,
        () -> revenueCalculationService.calculateRevenueForOneShop(start, end, 1L));
  }

  @Test
  void calculateRevenueForAllShops() {
    Mockito.doReturn(Arrays.asList(shop1, shop2)).when(shopRepository).findAll();
    Mockito.doReturn(16.0)
        .when(revenueCalculationService)
        .calculateRevenueForOneShop(start, end, 1L);
    Mockito.doReturn(1.0)
        .when(revenueCalculationService)
        .calculateRevenueForOneShop(start, end, 2L);
    Map<Shop, Double> shopDoubleMap =
        revenueCalculationService.calculateRevenueForAllShops(start, end);
    assertEquals(2, shopDoubleMap.size());
    assertEquals(16.0, shopDoubleMap.get(shop1));
    assertEquals(1.0, shopDoubleMap.get(shop2));
  }

  @Test
  void calculateAverageCheckForOneShop() {
    Mockito.doReturn(true).when(shopRepository).existsById(1L);
    Mockito.doReturn(Arrays.asList(order, order1))
        .when(orderRepository)
        .findByOrderedAtBetweenAndShopIdAndIsConfirmedTrue(start, end, 1L);
    double average = revenueCalculationService.calculateAverageCheckForOneShop(start, end, 1L);
    assertEquals(average, 8.0);
  }

  @Test
  void calculateAverageCheckForAllShops() {
    Mockito.doReturn(Arrays.asList(shop1, shop2)).when(shopRepository).findAll();
    Mockito.doReturn(8.0)
        .when(revenueCalculationService)
        .calculateAverageCheckForOneShop(start, end, 1L);
    Mockito.doReturn(6.0)
        .when(revenueCalculationService)
        .calculateAverageCheckForOneShop(start, end, 2L);
    Map<Shop, Double> shopDoubleMap =
        revenueCalculationService.calculateAverageCheckForAllShops(start, end);
    assertEquals(2, shopDoubleMap.size());
    assertEquals(8.0, shopDoubleMap.get(shop1));
    assertEquals(6.0, shopDoubleMap.get(shop2));
  }
}
