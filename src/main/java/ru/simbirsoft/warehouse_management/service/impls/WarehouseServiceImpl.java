package ru.simbirsoft.warehouse_management.service.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.simbirsoft.warehouse_management.dto.WarehouseDto;
import ru.simbirsoft.warehouse_management.dto.createForms.WarehouseCreateDto;
import ru.simbirsoft.warehouse_management.dto.mapper.WarehouseMapper;
import ru.simbirsoft.warehouse_management.exception.NotFoundException;
import ru.simbirsoft.warehouse_management.model.ItemWarehouse;
import ru.simbirsoft.warehouse_management.model.Warehouse;
import ru.simbirsoft.warehouse_management.model.pk.ItemWarehousePk;
import ru.simbirsoft.warehouse_management.repository.ItemWarehouseRepository;
import ru.simbirsoft.warehouse_management.repository.WarehouseRepository;
import ru.simbirsoft.warehouse_management.service.WarehouseService;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

  private final WarehouseRepository warehouseRepository;
  private final ItemWarehouseRepository itemWarehouseRepository;
  private final WarehouseMapper warehouseMapper;

  @Override
  public WarehouseDto createWarehouse(WarehouseCreateDto warehouseCreateDto) {
    Warehouse warehouse = Warehouse.builder().address(warehouseCreateDto.getAddress()).build();
    return warehouseMapper.mapToDto(warehouseRepository.save(warehouse));
  }

  @Override
  public void deleteWarehouse(Long id) {
    if (warehouseRepository.existsById(id)) {
      warehouseRepository.deleteById(id);
    } else throw new NotFoundException(Warehouse.class, "id", String.valueOf(id));
  }

  @Override
  public List<WarehouseDto> getAll() {
    return warehouseMapper.mapListDto(warehouseRepository.findAll());
  }

  @Override
  public WarehouseDto getById(Long id) {
    if (warehouseRepository.existsById(id)) {
      return warehouseMapper.mapToDto(warehouseRepository.getOne(id));
    } else throw new NotFoundException(Warehouse.class, "id", String.valueOf(id));
  }

  @Override
  public ItemWarehouse getItemFromWarehouse(Long warehouseId, Long itemId) {
    ItemWarehousePk itemWarehouseId = new ItemWarehousePk(itemId, warehouseId);
    if (itemWarehouseRepository.existsById(itemWarehouseId)) {
      return itemWarehouseRepository.getOne(itemWarehouseId);
    } else
      throw new NotFoundException(
          ItemWarehouse.class,
          "warehouse id",
          String.valueOf(warehouseId),
          "item id",
          String.valueOf(itemId));
  }

  @Override
  @Transactional
  public void putItem(ItemWarehouse itemWarehouse) {
    if (itemWarehouseRepository.existsById(itemWarehouse.getId())) {
      ItemWarehouse alreadySaved = itemWarehouseRepository.getOne(itemWarehouse.getId());
      alreadySaved.setCount(alreadySaved.getCount() + itemWarehouse.getCount());
    } else {
      itemWarehouseRepository.save(itemWarehouse);
    }
  }

  @Override
  public void putAllItems(List<ItemWarehouse> itemWarehouses) {
    itemWarehouses.forEach(this::putItem);
  }

  @Override
  @Transactional
  public void removeItem(ItemWarehouse toDelete) {
    if (itemWarehouseRepository.existsById(toDelete.getId())) {
      ItemWarehouse itemInWarehouse = itemWarehouseRepository.getOne(toDelete.getId());
      if (itemInWarehouse.getCount() > toDelete.getCount()) {
        itemInWarehouse.setCount(itemInWarehouse.getCount() - toDelete.getCount());
      } else if (itemInWarehouse.getCount().equals(toDelete.getCount())) {
        itemWarehouseRepository.delete(itemInWarehouse);
      } else throw new IllegalStateException();
    } else {
      throw new IllegalStateException(
          "Item with " + toDelete.getItem().getId() + " doesnt exist in this warehouse");
    }
  }

  @Override
  public void removeAllItems(List<ItemWarehouse> itemWarehouses) {
    itemWarehouses.forEach(this::removeItem);
  }
}
