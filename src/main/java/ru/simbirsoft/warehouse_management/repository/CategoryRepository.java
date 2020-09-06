package ru.simbirsoft.warehouse_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simbirsoft.warehouse_management.model.Category;

/** Jpa repository for Invoice entity */
public interface CategoryRepository extends JpaRepository<Category, Long> {}
