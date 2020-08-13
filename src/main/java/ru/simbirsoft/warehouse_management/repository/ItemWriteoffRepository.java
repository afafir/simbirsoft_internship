package ru.simbirsoft.warehouse_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simbirsoft.warehouse_management.model.ItemWriteoff;
import ru.simbirsoft.warehouse_management.model.pk.ItemWriteoffPk;

public interface ItemWriteoffRepository extends JpaRepository<ItemWriteoff, ItemWriteoffPk> {
}
