package ru.simbirsoft.warehouse_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simbirsoft.warehouse_management.model.Supplier;
import ru.simbirsoft.warehouse_management.model.user.User;

import java.util.Optional;

/** Jpa repository for Supplier */
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
  /**
   * Returns optional of Supplier with such name
   *
   * @param name - name of the supplier
   * @return Suppliers optional
   */
  Optional<Supplier> findByName(String name);
}
