package ru.simbirsoft.warehouse_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.simbirsoft.warehouse_management.model.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/** Jpa repository for Order entity */
public interface OrderRepository extends JpaRepository<Order, Long> {
  /**
   * * Checks if there is a unconfirmed order for user
   *
   * @param userId - id of the customer
   * @return true if unconfirmed order exists for user, otherwise returns false
   */
  boolean existsByCustomerIdAndIsConfirmedFalse(Long userId);
  /**
   * Optional of last unconfirmed order for customer
   *
   * @param userId - customer id
   * @return Optional of order
   */
  Optional<Order> findByCustomerIdAndIsConfirmedFalse(Long userId);

  /**
   * Returns all users orders
   *
   * @param userId - id of the customer
   * @return List of users orders
   */
  List<Order> findByCustomerId(Long userId);

  /**
   * Returns list of orders which were confirmed at the specified time
   *
   * @param start - time from data taken
   * @param end - time until data taken
   * @return List of orders
   */
  @Query(
      "SELECT order FROM Order  order WHERE "
          + "order.isConfirmed = true "
          + "AND order.orderedAt BETWEEN :start AND :end ")
  List<Order> findAllConfirmedBetweenDate(
      @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

  /**
   * Returns list of orders which were confirmed at the specified time in the specified shop
   *
   * @param start - time from data taken
   * @param end - time until data taken
   * @param shopId - id of the shop
   * @return List of orders
   */
  @Query(
      "SELECT order FROM Order  order WHERE "
          + "order.isConfirmed = true "
          + "AND order.orderedAt BETWEEN :start AND :end ")
  List<Order> findAllConfirmedBetweenDateForShop(
      @Param("start") LocalDateTime start,
      @Param("end") LocalDateTime end,
      @Param("shopId") Long shopId);
}
