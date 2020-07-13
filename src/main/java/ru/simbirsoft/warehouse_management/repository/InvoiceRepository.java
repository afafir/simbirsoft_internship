package ru.simbirsoft.warehouse_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simbirsoft.warehouse_management.model.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
