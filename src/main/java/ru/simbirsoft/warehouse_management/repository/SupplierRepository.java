package ru.simbirsoft.warehouse_management.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.simbirsoft.warehouse_management.model.Supplier;
import ru.simbirsoft.warehouse_management.model.user.User;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Optional<Supplier> findByName(String name);
}
