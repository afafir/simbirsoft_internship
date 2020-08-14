package ru.simbirsoft.warehouse_management.service.impls;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.simbirsoft.warehouse_management.exception.NotFoundException;
import ru.simbirsoft.warehouse_management.model.*;
import ru.simbirsoft.warehouse_management.model.pk.ItemWriteoffPk;
import ru.simbirsoft.warehouse_management.model.pk.OrderItemPk;
import ru.simbirsoft.warehouse_management.repository.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ItemTransactionCounterServiceImplTest {

  @InjectMocks private ItemTransactionCounterServiceImpl itemTransactionCounterService;

  @Mock private OrderRepository orderRepository;
  @Mock private ShopRepository shopRepository;
  @Mock private WriteoffRepository writeoffRepository;
  @Mock private WarehouseRepository warehouseRepository;

  private Order order;
  private Order order1;
  private Order order2;
  private Shop shop1;
  private Shop shop2;
  private Item item;
  private Item item1;
  private Warehouse warehouse1;
  private Warehouse warehouse2;
  private Writeoff writeoff;
  private Writeoff writeoff1;
  private Writeoff writeoff2;
  private LocalDateTime start;
  private LocalDateTime end;

  @BeforeEach
  private void before() {
    start = LocalDateTime.now().minusWeeks(1);
    end = LocalDateTime.now();

    item = Item.builder().name("item").price(1.0F).description("desc").build();
    item1 = Item.builder().name("item1").price(2.0F).description("desc1").build();

    shop1 = Shop.builder().name("shop1").build();
    shop2 = Shop.builder().name("shop2").build();

    warehouse1 = Warehouse.builder().address("address1").build();
    warehouse2 = Warehouse.builder().address("address2").build();

    order = Order.builder().shop(shop1).isConfirmed(true).build();
    order1 = Order.builder().shop(shop1).isConfirmed(true).build();
    order2 = Order.builder().shop(shop2).isConfirmed(true).build();

    writeoff = Writeoff.builder().warehouse(warehouse1).confirmed(true).build();
    writeoff1 = Writeoff.builder().warehouse(warehouse1).confirmed(true).build();
    writeoff2 = Writeoff.builder().warehouse(warehouse2).confirmed(true).build();

    OrderItem orderItem = new OrderItem(new OrderItemPk(1L, 1L), item, order, 2);
    OrderItem orderItem1 = new OrderItem(new OrderItemPk(2L, 1L), item1, order, 5);
    OrderItem orderItem2 = new OrderItem(new OrderItemPk(1L, 2L), item, order1, 4);
    OrderItem orderItem3 = new OrderItem(new OrderItemPk(2L, 3L), item1, order2, 3);

    ItemWriteoff itemWriteoff = new ItemWriteoff(new ItemWriteoffPk(1L, 1L), item, writeoff, 2);
    ItemWriteoff itemWriteoff1 = new ItemWriteoff(new ItemWriteoffPk(2L, 1L), item1, writeoff, 5);
    ItemWriteoff itemWriteoff2 = new ItemWriteoff(new ItemWriteoffPk(1L, 2L), item, writeoff1, 4);
    ItemWriteoff itemWriteoff3 = new ItemWriteoff(new ItemWriteoffPk(2L, 3L), item1, writeoff2, 3);

    order.setItems(Arrays.asList(orderItem, orderItem1));
    order1.setItems(Collections.singletonList(orderItem2));
    order2.setItems(Collections.singletonList(orderItem3));

    writeoff.setItems(Arrays.asList(itemWriteoff, itemWriteoff1));
    writeoff1.setItems(Collections.singletonList(itemWriteoff2));
    writeoff2.setItems(Collections.singletonList(itemWriteoff3));
  }

  @Test
  void getCountOfSaledItemsForOneShop() {
    Mockito.doReturn(true).when(shopRepository).existsById(1L);
    Mockito.doReturn(Arrays.asList(order, order1))
        .when(orderRepository)
        .findByOrderedAtBetweenAndShopIdAndIsConfirmedTrue(start, end, 1L);
    Map<Item, Integer> countOfSaledItemsForOneShop =
        itemTransactionCounterService.getCountOfSaledItemsForOneShop(start, end, 1L);
    assertEquals(6, countOfSaledItemsForOneShop.get(item));
    assertEquals(5, countOfSaledItemsForOneShop.get(item1));
    assertEquals(2, countOfSaledItemsForOneShop.size());
  }

  @Test
  void getCountOfSaledItemsForOneShopThatDoesntExist() {
    Mockito.doReturn(false).when(shopRepository).existsById(1L);
    assertThrows(
        NotFoundException.class,
        () -> itemTransactionCounterService.getCountOfSaledItemsForOneShop(start, end, 1L));
  }

  @Test
  void getCountOfSaledItemsForAllShop() {
    Mockito.doReturn(Arrays.asList(order, order1, order2))
        .when(orderRepository)
        .findByOrderedAtBetweenAndIsConfirmedTrue(start, end);
    Map<Shop, Map<Item, Integer>> countOfSaledItemsForAllShop =
        itemTransactionCounterService.getCountOfSaledItemsForAllShop(start, end);
    assertEquals(2, countOfSaledItemsForAllShop.size());
    assertEquals(6, countOfSaledItemsForAllShop.get(shop1).get(item));
    assertEquals(5, countOfSaledItemsForAllShop.get(shop1).get(item1));
    assertEquals(3, countOfSaledItemsForAllShop.get(shop2).get(item1));
  }

  @Test
  void getCountOfWriteoffedItemsForAllWarehouses() {
    Mockito.doReturn(Arrays.asList(writeoff, writeoff1, writeoff2))
        .when(writeoffRepository)
        .findAllBetweenDates(start, end);
    Map<Warehouse, Map<Item, Integer>> countOfWriteoffedItemsForAllWarehouses =
        itemTransactionCounterService.getCountOfWriteoffedItemsForAllWarehouses(start, end);
    assertEquals(2, countOfWriteoffedItemsForAllWarehouses.size());
    assertEquals(6, countOfWriteoffedItemsForAllWarehouses.get(warehouse1).get(item));
    assertEquals(5, countOfWriteoffedItemsForAllWarehouses.get(warehouse1).get(item1));
    assertEquals(3, countOfWriteoffedItemsForAllWarehouses.get(warehouse2).get(item1));
  }

  @Test
  void getCountOfWriteoffedItemsForOneWarehouses() {
    Mockito.doReturn(true).when(warehouseRepository).existsById(1L);
    Mockito.doReturn(Arrays.asList(writeoff, writeoff1))
        .when(writeoffRepository)
        .findAllBetweenDatesForWarehouse(start, end, 1L);
    Map<Item, Integer> countOfWriteoffedItemsForOneWarehouses =
        itemTransactionCounterService.getCountOfWriteoffedItemsForOneWarehouses(start, end, 1L);
    assertEquals(6, countOfWriteoffedItemsForOneWarehouses.get(item));
    assertEquals(5, countOfWriteoffedItemsForOneWarehouses.get(item1));
    assertEquals(2, countOfWriteoffedItemsForOneWarehouses.size());
  }

  @Test
  void getCountOfWriteoffedItemsForOneWarehousesThatDoesntExist() {
    Mockito.doReturn(false).when(warehouseRepository).existsById(1L);
    assertThrows(
        NotFoundException.class,
        () ->
            itemTransactionCounterService.getCountOfWriteoffedItemsForOneWarehouses(
                start, end, 1L));
  }
}
