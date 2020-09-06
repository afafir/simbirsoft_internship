package ru.simbirsoft.warehouse_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simbirsoft.warehouse_management.model.Invoice;

import java.util.List;

/** Jpa repository for Invoice entity */
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    /**
     *
     * @return List of unconfirmed invoices
     */
    List<Invoice> findByConfirmedFalse();
}
