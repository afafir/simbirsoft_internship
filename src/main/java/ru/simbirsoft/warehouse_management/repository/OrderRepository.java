package ru.simbirsoft.warehouse_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simbirsoft.warehouse_management.model.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    boolean existsByCustomerIdAndIsConfirmedFalse(Long userId);
    List<Order> findByCustomerId(Long userId);
    List<Order> findByOrderedAtBetweenAndIsConfirmedTrue(LocalDateTime start, LocalDateTime end);
    List<Order> findByOrderedAtBetweenAndShopIdAndIsConfirmedTrue(LocalDateTime start, LocalDateTime end, Long id);
    Optional<Order> findByCustomerIdAndIsConfirmedFalse(Long userId);

}
