package ru.simbirsoft.warehouse_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simbirsoft.warehouse_management.model.Writeoff;

import java.util.List;

public interface WriteoffRepository extends JpaRepository<Writeoff, Long> {
  List<Writeoff> findByConfirmedFalse();
}
