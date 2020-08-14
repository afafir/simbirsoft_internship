package ru.simbirsoft.warehouse_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simbirsoft.warehouse_management.model.ItemInvoice;
import ru.simbirsoft.warehouse_management.model.pk.ItemInvoicePk;

/** Jpa repository for ItemInvoice entity */
public interface ItemInvoiceRepository extends JpaRepository<ItemInvoice, ItemInvoicePk> {}
