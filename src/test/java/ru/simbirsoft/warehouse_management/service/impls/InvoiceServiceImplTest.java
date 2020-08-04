package ru.simbirsoft.warehouse_management.service.impls;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.simbirsoft.warehouse_management.dto.InvoiceDto;
import ru.simbirsoft.warehouse_management.dto.createForms.InvoiceCreateDto;
import ru.simbirsoft.warehouse_management.exception.NotFoundException;
import ru.simbirsoft.warehouse_management.model.Invoice;
import ru.simbirsoft.warehouse_management.model.ItemWarehouse;
import ru.simbirsoft.warehouse_management.model.pk.ItemWarehousePk;
import ru.simbirsoft.warehouse_management.repository.InvoiceRepository;
import ru.simbirsoft.warehouse_management.repository.ItemWarehouseRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql({"/sql/dropTable.sql", "/sql/table.sql", "/sql/dataTest.sql"})
class InvoiceServiceImplTest {

  @Autowired private InvoiceServiceImpl invoiceService;

  @Autowired private ItemWarehouseRepository itemWarehouseRepository;

  @Autowired private InvoiceRepository invoiceRepository;

  @Test
  @Transactional
  void createInvoice() {
    InvoiceCreateDto invoiceCreateDto =
        InvoiceCreateDto.builder()
            .supplierId(1L)
            .localDateTime(LocalDateTime.now())
            .itemCount(Map.of(1L, 2, 2L, 1))
            .warehouseId(1L)
            .build();
    InvoiceDto invoiceDto = invoiceService.createInvoice(invoiceCreateDto);
    assertEquals(invoiceDto.getArrivedAt().getDayOfMonth(), 4);
    assertEquals(false, invoiceDto.getConfirmed());
    assertEquals(1L, invoiceDto.getSupplier().getId());
    assertEquals(1L, invoiceDto.getWarehouse().getId());
    assertEquals(1L, invoiceDto.getItems().get(0).getItem().getId());
  }

  @Test
  @Transactional
  void createInvoiceWithWrongItem() {
    InvoiceCreateDto invoiceCreateDto =
        InvoiceCreateDto.builder()
            .supplierId(1L)
            .localDateTime(LocalDateTime.now())
            .itemCount(Map.of(322L, 2, 2L, 1))
            .warehouseId(1L)
            .build();
    assertThrows(NotFoundException.class, () -> invoiceService.createInvoice(invoiceCreateDto));
  }

  @Test
  void confirmInvoiceThatAlreadyConfirmed() {
    assertThrows(IllegalStateException.class, () -> invoiceService.confirmInvoice(7L));
  }

  @Test
  void confirmInvoiceThatDoesntExist() {
    assertThrows(NotFoundException.class, () -> invoiceService.confirmInvoice(155L));
  }

  @Test
  @Transactional
  void confirmInvoice() {
    invoiceService.confirmInvoice(8L);
    Invoice invoice = invoiceRepository.getOne(8L);
    // проверяю, подтвердилась ли накладная
    assertTrue(invoice.getConfirmed());
    // проверяю, пополнился ли склад
    ItemWarehouse itemWarehouse = itemWarehouseRepository.getOne(new ItemWarehousePk(1L, 1L));
    assertEquals(2, itemWarehouse.getCount());
  }

  @Test
  @Transactional
  void getNotConfirmed() {
    List<InvoiceDto> invoices = invoiceService.getNotConfirmed();
    for (InvoiceDto invoice : invoices) {
      assertFalse(invoice.getConfirmed());
    }
  }

  @Test
  @Transactional
  void getInvoiceById() {
    InvoiceDto invoiceDto = invoiceService.getInvoiceById(6L);
    assertTrue(invoiceDto.getConfirmed());
    assertEquals(1L, invoiceDto.getWarehouse().getId());
  }

  @Test
  @Transactional
  void getInvoiceByThatDoesntExist() {
    assertThrows(NotFoundException.class, () -> invoiceService.getInvoiceById(332L));
  }

  @Test
  @Transactional
  void deleteById() {
    invoiceService.deleteById(8L);
    Optional<Invoice> optionalInvoiceDto = invoiceRepository.findById(8L);
    assertFalse(optionalInvoiceDto.isPresent());
  }

  @Test
  @Transactional
  void deleteByIdThatConfirmed() {
    assertThrows(IllegalStateException.class, () -> invoiceService.deleteById(6L));
  }

  @Test
  void deleteByIdThatDoesntExist() {
    assertThrows(NotFoundException.class, () -> invoiceService.deleteById(13231L));
  }
}
