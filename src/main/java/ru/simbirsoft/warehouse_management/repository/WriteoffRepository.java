package ru.simbirsoft.warehouse_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simbirsoft.warehouse_management.model.Writeoff;

import java.time.LocalDateTime;
import java.util.List;

public interface WriteoffRepository extends JpaRepository<Writeoff, Long> {
  List<Writeoff> findByConfirmedFalse();
  List<Writeoff> findByTimeBetweenAndConfirmedTrue(LocalDateTime start, LocalDateTime end);
  List<Writeoff> findByTimeBetweenAndWarehouseIdAndConfirmedTrue(LocalDateTime start, LocalDateTime end, Long warehouseId);

}
