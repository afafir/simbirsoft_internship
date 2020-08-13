package ru.simbirsoft.warehouse_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simbirsoft.warehouse_management.dto.OrderDto;
import ru.simbirsoft.warehouse_management.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    boolean existsByCustomerIdAndIsConfirmedFalse(Long userId);
    List<Order> findByCustomerId(Long userId);
    Optional<Order> findByCustomerIdAndIsConfirmedFalse(Long userId);

}
