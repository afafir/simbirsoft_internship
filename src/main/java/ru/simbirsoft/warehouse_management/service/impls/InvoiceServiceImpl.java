package ru.simbirsoft.warehouse_management.service.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.simbirsoft.warehouse_management.dto.InvoiceDto;
import ru.simbirsoft.warehouse_management.dto.createForms.InvoiceCreateDto;
import ru.simbirsoft.warehouse_management.dto.mapper.InvoiceMapper;
import ru.simbirsoft.warehouse_management.dto.mapper.ItemMapper;
import ru.simbirsoft.warehouse_management.dto.mapper.SupplierMapper;
import ru.simbirsoft.warehouse_management.dto.mapper.WarehouseMapper;
import ru.simbirsoft.warehouse_management.exception.NotFoundException;
import ru.simbirsoft.warehouse_management.model.*;
import ru.simbirsoft.warehouse_management.model.pk.ItemInvoicePk;
import ru.simbirsoft.warehouse_management.model.pk.ItemWarehousePk;
import ru.simbirsoft.warehouse_management.repository.InvoiceRepository;
import ru.simbirsoft.warehouse_management.repository.ItemInvoiceRepository;
import ru.simbirsoft.warehouse_management.service.InvoiceService;
import ru.simbirsoft.warehouse_management.service.ItemService;
import ru.simbirsoft.warehouse_management.service.SupplierService;
import ru.simbirsoft.warehouse_management.service.WarehouseService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

  // mappers
  private final InvoiceMapper invoiceMapper;
  private final ItemMapper itemMapper;
  private final WarehouseMapper warehouseMapper;
  private final SupplierMapper supplierMapper;

  // services
  private final ItemService itemService;
  private final WarehouseService warehouseService;
  private final SupplierService supplierService;

  // repositories
  private final InvoiceRepository invoiceRepository;
  private final ItemInvoiceRepository itemInvoiceRepository;

  @Override
  public InvoiceDto createInvoice(InvoiceCreateDto invoiceCreateDto) {
    Invoice invoice =
        Invoice.builder().confirmed(false).arrivedAt(invoiceCreateDto.getLocalDateTime()).build();
    Warehouse warehouse =
        warehouseMapper.map(warehouseService.getById(invoiceCreateDto.getWarehouseId()));
    Supplier supplier =
        supplierMapper.map(supplierService.getById(invoiceCreateDto.getSupplierId()));
    invoice.setWarehouse(warehouse);
    invoice.setSupplier(supplier);
    invoice = invoiceRepository.save(invoice);
    List<ItemInvoice> itemCounts = new ArrayList<>();
    for (Map.Entry<Long, Integer> entry : invoiceCreateDto.getItemCount().entrySet()) {
      itemCounts.add(
          ItemInvoice.builder()
              .id(new ItemInvoicePk(entry.getKey(), invoice.getId()))
              .count(entry.getValue())
              .item(itemMapper.map(itemService.getItem(entry.getKey())))
              .build());
    }
    itemInvoiceRepository.saveAll(itemCounts);
    invoice.setItems(itemCounts);
    return invoiceMapper.mapToDto(invoice);
  }

  @Override
  @Transactional
  public void confirmInvoice(Long id) {
    if (invoiceRepository.existsById(id)) {
      Invoice invoice = invoiceRepository.getOne(id);
      if (invoice.getConfirmed()) {
        throw new IllegalStateException("Invoice already confirmed");
      }
      List<ItemWarehouse> toPut =
          invoice.getItems().stream()
              .map(
                  x ->
                      ItemWarehouse.builder()
                          .id(
                              new ItemWarehousePk(
                                  x.getItem().getId(), invoice.getWarehouse().getId()))
                          .warehouse(invoice.getWarehouse())
                          .count(x.getCount())
                          .item(x.getItem())
                          .build())
              .collect(Collectors.toList());
      warehouseService.putAllItems(toPut);
      invoice.setConfirmed(true);
    } else throw new NotFoundException(Invoice.class, "id", String.valueOf(id));
  }

  @Override
  public List<InvoiceDto> getNotConfirmed() {
    return invoiceMapper.mapListDto(invoiceRepository.findByConfirmedFalse());
  }

  @Override
  public InvoiceDto getInvoiceById(Long id) {
    if (invoiceRepository.existsById(id)) {
      return invoiceMapper.mapToDto(invoiceRepository.getOne(id));
    } else throw new NotFoundException(Invoice.class, "id", String.valueOf(id));
  }

  @Override
  public void deleteById(Long id) {
    if (invoiceRepository.existsById(id)) {
      if (invoiceRepository.getOne(id).getConfirmed()) {
        throw new IllegalStateException("You cant delete confirmed invoice");
      }
      invoiceRepository.deleteById(id);
    } else throw new NotFoundException(Invoice.class, "id", String.valueOf(id));
  }
}
