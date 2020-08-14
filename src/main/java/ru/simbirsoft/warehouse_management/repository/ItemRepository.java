package ru.simbirsoft.warehouse_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simbirsoft.warehouse_management.model.Item;

/** Jpa repository for Item entity */
public interface ItemRepository extends JpaRepository<Item, Long> {
}
