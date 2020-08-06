package ru.simbirsoft.warehouse_management.service;

import ru.simbirsoft.warehouse_management.dto.InvoiceDto;
import ru.simbirsoft.warehouse_management.dto.createForms.InvoiceCreateDto;

import java.util.List;

public interface InvoiceService {
    /**
     * Creates a new invoice from Creating form.
     *
     *
     * @param invoiceCreateDto - invoice create form. Contatins id of warehouse, ids of items and their counts, id of supplier
     * @return InvoiceDto - dto of created invoice
     */
    InvoiceDto createInvoice(InvoiceCreateDto invoiceCreateDto);

  /**
   * @param id - id of invoice
   * @return dto of founded invoice.
   * @throws ru.simbirsoft.warehouse_management.exception.NotFoundException if entity with such id doesnt exist
   */
  InvoiceDto getInvoiceById(Long id);

    /**
     * Deletes invoice from database. But if invoice already confirmed, doesnt delete it
     * @param id - id of invoice to delete
     * @throws ru.simbirsoft.warehouse_management.exception.NotFoundException if entity with such id doesnt exist
     * @throws IllegalStateException if invoice confirmed
     */
    void deleteById(Long id);

    /**
     * Confirm invoice. It replenishes the warehouse with the number of products specified in the invoice
     *
     *
     * @param invoiceId - id of the invoice
     * @throws IllegalStateException if invoice already confirmed
     */
    void confirmInvoice(Long invoiceId);

    /**
     * Return dto list of not confirmed invoices
     * @return list of not confirmed invoices
     */
    List<InvoiceDto> getNotConfirmed();

}
