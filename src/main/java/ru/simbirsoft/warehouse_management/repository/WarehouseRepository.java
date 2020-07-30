package ru.simbirsoft.warehouse_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simbirsoft.warehouse_management.model.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {}
