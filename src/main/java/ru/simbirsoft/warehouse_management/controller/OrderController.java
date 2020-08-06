package ru.simbirsoft.warehouse_management.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import liquibase.pro.packaged.L;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.warehouse_management.dto.OrderDto;
import ru.simbirsoft.warehouse_management.dto.createForms.OrderCreateForm;
import ru.simbirsoft.warehouse_management.dto.createForms.OrderItemAddForm;
import ru.simbirsoft.warehouse_management.dto.createForms.OrderItemDeleteForm;
import ru.simbirsoft.warehouse_management.security.jwt.authentication.UserDetailsImpl;
import ru.simbirsoft.warehouse_management.service.OrderService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api("order controller")
public class OrderController {

  private final OrderService orderService;

  @PreAuthorize("isAuthenticated()")
  @PostMapping("${url.order.new}")
  public OrderDto createOrder(@RequestBody @Valid OrderCreateForm orderCreateForm) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    orderCreateForm.setCustomerId(((UserDetailsImpl) authentication.getPrincipal()).getUserId());
    return orderService.createOrder(orderCreateForm);
  }

  @PreAuthorize("isAuthenticated()")
  @PutMapping("${url.order.addItem}")
  public OrderDto addItemsToOrder(@RequestBody @Valid OrderItemAddForm orderItemAddForm) {
    return orderService.addItemToOrder(
        orderItemAddForm.getOrderId(), orderItemAddForm.getItemId(), orderItemAddForm.getCount());
  }

  @PreAuthorize("isAuthenticated()")
  @PutMapping("${url.order.removeItem}")
  public OrderDto removeItemFromOrder(@RequestBody @Valid OrderItemDeleteForm orderItemDeleteForm) {
    return orderService.deleteItemFromOrder(
        orderItemDeleteForm.getOrderId(),
        orderItemDeleteForm.getItemId(),
        orderItemDeleteForm.getCount());
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("${url.order}/{id}")
  public OrderDto getById(@PathVariable @ApiParam("Id of the order") Long id) {
    return orderService.getById(id);
  }

  @PreAuthorize("isAuthenticated()")
  @PutMapping("${url.order.confirm}")
  public OrderDto confirmOrder(@ApiParam("Id of the order") @RequestParam Long orderId) {
    return orderService.confirmOrder(orderId);
  }

  @PreAuthorize("isAuthenticated()")
  @DeleteMapping("${url.order.decline}")
  public OrderDto declineOrder(@ApiParam("Id of the order") @RequestParam Long orderId) {
    return orderService.declineOrder(orderId);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping("${url.order}")
  public List<OrderDto> getAll() {
    return orderService.getAllOrders();
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("${url.order.userOrders}/{id}")
  public List<OrderDto> getAllUserOrders(@ApiParam("id of the user") @PathVariable Long id) {
    return orderService.getAllOrderForUser(id);
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("${url.order.lastUnconfirmed}/{id}")
  public OrderDto getLastUnconfirmedOrderForUser(
      @ApiParam("id of the user") @PathVariable Long id) {
    return orderService.getLastUnconfirmedOrderForUser(id);
  }
}
