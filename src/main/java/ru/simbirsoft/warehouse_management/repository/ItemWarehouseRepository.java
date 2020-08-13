package ru.simbirsoft.warehouse_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simbirsoft.warehouse_management.model.ItemWarehouse;
import ru.simbirsoft.warehouse_management.model.pk.ItemWarehousePk;

public interface ItemWarehouseRepository extends JpaRepository<ItemWarehouse, ItemWarehousePk> {
}
