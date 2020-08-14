package ru.simbirsoft.warehouse_management.service.impls;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import ru.simbirsoft.warehouse_management.dto.ShopDto;
import ru.simbirsoft.warehouse_management.dto.WarehouseDto;
import ru.simbirsoft.warehouse_management.dto.mapper.ShopMapper;
import ru.simbirsoft.warehouse_management.dto.mapper.WarehouseMapper;
import ru.simbirsoft.warehouse_management.model.*;
import ru.simbirsoft.warehouse_management.model.pk.ItemWriteoffPk;
import ru.simbirsoft.warehouse_management.model.pk.OrderItemPk;
import ru.simbirsoft.warehouse_management.service.ItemTransactionCounterService;
import ru.simbirsoft.warehouse_management.service.ShopService;
import ru.simbirsoft.warehouse_management.service.WarehouseService;
import ru.simbirsoft.warehouse_management.util.impl.ItextPdfReportUtil;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ReportServiceImplTest {

  @InjectMocks private ReportServiceImpl reportService;

  @Mock private ItemTransactionCounterService salesCounterService;
  @Mock private RevenueCalculationServiceImpl revenueCalculationService;
  @Mock private ShopService shopService;
  @Mock private WarehouseService warehouseService;
  @Mock private ShopMapper shopMapper;
  @Mock private WarehouseMapper warehouseMapper;

  private Shop shop1;
  private Shop shop2;
  private Item item;
  private Item item1;
  private Warehouse warehouse1;
  private Warehouse warehouse2;
  private LocalDateTime start;
  private LocalDateTime end;

  @BeforeEach
  void before() {
    ReflectionTestUtils.setField(reportService, "pdfReportUtil", new ItextPdfReportUtil());
    start = LocalDateTime.now().minusWeeks(1);
    end = LocalDateTime.now();

    item = Item.builder().name("item").price(1.0F).description("desc").build();
    item1 = Item.builder().name("item1").price(2.0F).description("desc1").build();

    shop1 = Shop.builder().name("shop1").build();
    shop2 = Shop.builder().name("shop2").build();

    warehouse1 = Warehouse.builder().address("address1").build();
    warehouse2 = Warehouse.builder().address("address2").build();

    Order order = Order.builder().shop(shop1).isConfirmed(true).build();
    Order order1 = Order.builder().shop(shop1).isConfirmed(true).build();
    Order order2 = Order.builder().shop(shop2).isConfirmed(true).build();

    Writeoff writeoff = Writeoff.builder().warehouse(warehouse1).confirmed(true).build();
    Writeoff writeoff1 = Writeoff.builder().warehouse(warehouse1).confirmed(true).build();
    Writeoff writeoff2 = Writeoff.builder().warehouse(warehouse2).confirmed(true).build();

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
  void allShopsOrderReport() {
    Mockito.doReturn(Map.of(shop1, Map.of(item1, 5, item, 3), shop2, Map.of(item, 10)))
        .when(salesCounterService)
        .getCountOfSaledItemsForAllShop(start, end);
    ByteArrayInputStream byteArrayInputStream = reportService.getAllShopsOrderReport(start, end);
    assertTrue(byteArrayInputStream.markSupported());
    assertNotEquals(0, byteArrayInputStream.available());
  }

  @Test
  void oneShopOrderReport() {
    Mockito.doReturn(Map.of(item1, 5, item, 3))
        .when(salesCounterService)
        .getCountOfSaledItemsForOneShop(start, end, 1L);
    Mockito.doReturn(ShopDto.builder().id(1L).name("shop1").build()).when(shopService).getById(1L);
    Mockito.doReturn(shop1).when(shopMapper).map(any());
    ByteArrayInputStream byteArrayInputStream = reportService.getOneShopOrderReport(1L, start, end);
    assertTrue(byteArrayInputStream.markSupported());
    assertNotEquals(0, byteArrayInputStream.available());
  }

  @Test
  void allWarehousesWriteoffReport() {
    Mockito.doReturn(Map.of(warehouse1, Map.of(item1, 5, item, 3), warehouse2, Map.of(item, 10)))
        .when(salesCounterService)
        .getCountOfWriteoffedItemsForAllWarehouses(start, end);
    ByteArrayInputStream byteArrayInputStream =
        reportService.getAllWarehousesWriteoffReport(start, end);
    assertTrue(byteArrayInputStream.markSupported());
    assertNotEquals(0, byteArrayInputStream.available());
  }

  @Test
  void oneWarehouseWriteoffReport() {
    Mockito.doReturn(Map.of(item1, 5, item, 3))
        .when(salesCounterService)
        .getCountOfWriteoffedItemsForOneWarehouses(start, end, 1L);
    Mockito.doReturn(WarehouseDto.builder().id(1L).address("address1").build())
        .when(warehouseService)
        .getById(1L);
    Mockito.doReturn(warehouse1).when(warehouseMapper).map(any());
    ByteArrayInputStream byteArrayInputStream =
        reportService.getOneWarehouseWriteoffReport(1L, start, end);
    assertTrue(byteArrayInputStream.markSupported());
    assertNotEquals(0, byteArrayInputStream.available());
  }

  @Test
  void allShopsRevenueReport() {
    Mockito.doReturn(Map.of(shop1, 1.0, shop2, 10.0))
        .when(revenueCalculationService)
        .calculateRevenueForAllShops(start, end);
    ByteArrayInputStream byteArrayInputStream = reportService.getAllShopsRevenueReport(start, end);
    assertTrue(byteArrayInputStream.markSupported());
    assertNotEquals(0, byteArrayInputStream.available());
  }

  @Test
  void oneShopRevenueReport() {
    Mockito.doReturn(10.0)
        .when(revenueCalculationService)
        .calculateRevenueForOneShop(start, end, 1L);
    Mockito.doReturn(ShopDto.builder().id(1L).name("shop1").build()).when(shopService).getById(1L);
    Mockito.doReturn(shop1).when(shopMapper).map(any());
    ByteArrayInputStream byteArrayInputStream = reportService.getOneShopRevenueReport(1L, start, end);
    assertTrue(byteArrayInputStream.markSupported());
    assertNotEquals(0, byteArrayInputStream.available());
  }

  @Test
  void allShopsAverageCheckReport() {
    Mockito.doReturn(Map.of(shop1, 1.0, shop2, 10.0))
        .when(revenueCalculationService)
        .calculateAverageCheckForAllShops(start, end);
    ByteArrayInputStream byteArrayInputStream =
        reportService.getAllShopsAverageCheckReport(start, end);
    assertTrue(byteArrayInputStream.markSupported());
    assertNotEquals(0, byteArrayInputStream.available());
  }

  @Test
  void oneShopAverageCheckReport() {
    Mockito.doReturn(10.0)
        .when(revenueCalculationService)
        .calculateAverageCheckForOneShop(start, end, 1L);
    Mockito.doReturn(ShopDto.builder().id(1L).name("shop1").build()).when(shopService).getById(1L);
    Mockito.doReturn(shop1).when(shopMapper).map(any());
    ByteArrayInputStream byteArrayInputStream =
        reportService.getOneShopAverageCheckReport(1L, start, end);
    assertTrue(byteArrayInputStream.markSupported());
    assertNotEquals(0, byteArrayInputStream.available());
  }
}
