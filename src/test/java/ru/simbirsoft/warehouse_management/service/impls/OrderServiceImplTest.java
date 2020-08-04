package ru.simbirsoft.warehouse_management.service.impls;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.simbirsoft.warehouse_management.dto.OrderDto;
import ru.simbirsoft.warehouse_management.dto.createForms.OrderCreateForm;
import ru.simbirsoft.warehouse_management.exception.NotFoundException;
import ru.simbirsoft.warehouse_management.model.ItemWarehouse;
import ru.simbirsoft.warehouse_management.model.pk.ItemWarehousePk;
import ru.simbirsoft.warehouse_management.repository.ItemWarehouseRepository;
import ru.simbirsoft.warehouse_management.repository.OrderRepository;
import ru.simbirsoft.warehouse_management.service.OrderService;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql({"/sql/dropTable.sql", "/sql/table.sql", "/sql/dataTest.sql"})
class OrderServiceImplTest {

  @Autowired OrderService orderService;

  @Autowired OrderRepository orderRepository;

  @Autowired ItemWarehouseRepository itemWarehouseRepository;

  @Test
  @Transactional
  void createOrder() {
    OrderCreateForm orderCreateForm = OrderCreateForm.builder().customerId(2L).shopId(2L).build();
    OrderDto orderDto = orderService.createOrder(orderCreateForm);
    assertEquals(0, orderDto.getCost());
    assertFalse(orderDto.getIsConfirmed());
  }

  @Test
  @Transactional
  void createOrderIfUserAlreadyHaveUnfinishedOrder() {
    OrderCreateForm orderCreateForm = OrderCreateForm.builder().customerId(1L).shopId(2L).build();
    assertThrows(IllegalStateException.class, ()->orderService.createOrder(orderCreateForm));
  }

  @Test
  @Transactional
  void createWithShopThatDoesntExist() {
    OrderCreateForm orderCreateForm =
        OrderCreateForm.builder().customerId(2L).shopId(12322L).build();
    assertThrows(NotFoundException.class, () -> orderService.createOrder(orderCreateForm));
  }

  @Test
  @Transactional
  void getById() {
    OrderDto orderDto = orderService.getById(2L);
    assertEquals(orderDto.getId(), 2L);
  }

  @Test
  @Transactional
  void getByIdThatDoesntExist() {
    assertThrows(NotFoundException.class, () -> orderService.getById(123124431L));
  }

  @Test
  @Transactional
  void addItemToOrder() {
    orderService.addItemToOrder(2L, 2L, 15);
    OrderDto orderDto = orderService.addItemToOrder(2L, 1L, 1);
    assertEquals(16, orderDto.getOrderItems().get(0).getCount());
    assertEquals(1, orderDto.getOrderItems().get(1).getCount());
  }

  @Test
  @Transactional
  void addItemThatDoenstExistToOrder() {
    assertThrows(NotFoundException.class, () -> orderService.addItemToOrder(2L, 212313L, 15));
  }

  @Test
  @Transactional
  void addItemThatDoesntSoMuch(){
    assertThrows(IllegalStateException.class, () -> orderService.addItemToOrder(2L, 2L, 15123));
  }

  @Test
  @Transactional
  void deleteItemFromOrder() {
    OrderDto orderDto = orderService.deleteItemFromOrder(2L, 2L, 1);
    assertEquals(orderDto.getOrderItems().size(), 0);
    //проверяю, вернулась ли вещь на склад
    ItemWarehouse itemWarehouse = itemWarehouseRepository.getOne(new ItemWarehousePk(2L, 1L));
    assertEquals(85, itemWarehouse.getCount());
  }

  @Test
  @Transactional
  void deleteItemThatDoesntExistFromOrder() {
    assertThrows(IllegalStateException.class, ()->    orderService.deleteItemFromOrder(2L, 2122L, 1));
  }

  @Test
  @Transactional
  void deleteItemFromOrderThatDoesntExist() {
    assertThrows(IllegalStateException.class, ()->    orderService.deleteItemFromOrder(2L, 2122L, 1));
  }

  @Test
  @Transactional
  void confirmOrder() {
    OrderDto orderDto = orderService.confirmOrder(2L);
    assertTrue(orderDto.getIsConfirmed());
  }

  @Test
  @Transactional
  void confirmAlreadyConfirmedOrder(){
    assertThrows(IllegalStateException.class, ()->orderService.confirmOrder(3L));
  }

  @Test
  @Transactional
  void declineOrder() {
    OrderDto orderDto = orderService.declineOrder(2L);
    assertFalse(orderRepository.existsById(2L));
    //проверяем, вернулась ли вещь в склад
    ItemWarehouse itemWarehouse = itemWarehouseRepository.getOne(new ItemWarehousePk(2L, 1L));
    assertEquals(85, itemWarehouse.getCount());
  }

  @Test
  @Transactional
  void declineOrderThatAlreadyConfirmed() {
    assertThrows(IllegalStateException.class, ()-> orderService.declineOrder(3L));
  }

  @Test
  @Transactional
  void getAllOrderForUser() {
    List<OrderDto> orderDtos = orderService.getAllOrderForUser(2L);
    for (OrderDto orderDto : orderDtos) {
      assertEquals(orderDto.getCustomer().getId(), 2L);
    }
  }

  @Test
  @Transactional
  void getAllOrders() {
    List<OrderDto> orderDtos = orderService.getAllOrders();
    assertEquals(2, orderDtos.size());
  }

  @Test
  @Transactional
  void getLastUnconfirmedOrderForUser() {
    OrderDto orderDto = orderService.getLastUnconfirmedOrderForUser(1L);
    assertFalse(orderDto.getIsConfirmed());
    assertEquals(1L, orderDto.getCustomer().getId());
  }
}
