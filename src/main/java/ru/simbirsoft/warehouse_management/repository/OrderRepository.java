package ru.simbirsoft.warehouse_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simbirsoft.warehouse_management.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
