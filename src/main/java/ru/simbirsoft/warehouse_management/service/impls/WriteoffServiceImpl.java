package ru.simbirsoft.warehouse_management.service.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.simbirsoft.warehouse_management.dto.WriteoffDto;
import ru.simbirsoft.warehouse_management.dto.createForms.WriteoffCreateDto;
import ru.simbirsoft.warehouse_management.dto.mapper.*;
import ru.simbirsoft.warehouse_management.exception.NotFoundException;
import ru.simbirsoft.warehouse_management.model.*;
import ru.simbirsoft.warehouse_management.model.pk.ItemWarehousePk;
import ru.simbirsoft.warehouse_management.model.pk.ItemWriteoffPk;
import ru.simbirsoft.warehouse_management.repository.ItemWriteoffRepository;
import ru.simbirsoft.warehouse_management.repository.WriteoffRepository;
import ru.simbirsoft.warehouse_management.service.ItemService;
import ru.simbirsoft.warehouse_management.service.WarehouseService;
import ru.simbirsoft.warehouse_management.service.WriteoffService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WriteoffServiceImpl implements WriteoffService {

  // mappers
  private final WriteoffMapper writeoffMapper;
  private final ItemMapper itemMapper;
  private final WarehouseMapper warehouseMapper;

  // services
  private final ItemService itemService;
  private final WarehouseService warehouseService;

  // repositories
  private final WriteoffRepository writeoffRepository;
  private final ItemWriteoffRepository itemWriteoffRepository;

  @Override
  public WriteoffDto createWriteoff(WriteoffCreateDto writeoffCreateDto) {
    Writeoff writeoff =
        Writeoff.builder().confirmed(false).time(writeoffCreateDto.getLocalDateTime()).build();
    Warehouse warehouse =
        warehouseMapper.map(warehouseService.getById(writeoffCreateDto.getWarehouseId()));
    writeoff.setWarehouse(warehouse);
    writeoff = writeoffRepository.save(writeoff);
    List<ItemWriteoff> itemCounts = new ArrayList<>();
    for (Map.Entry<Long, Integer> entry : writeoffCreateDto.getItemCount().entrySet()) {
      itemCounts.add(
          ItemWriteoff.builder()
              .id(new ItemWriteoffPk(entry.getKey(), writeoff.getId()))
              .count(entry.getValue())
              .item(itemMapper.map(itemService.getItem(entry.getKey())))
              .build());
    }
    itemWriteoffRepository.saveAll(itemCounts);
    writeoff.setItems(itemCounts);
    return writeoffMapper.mapToDto(writeoff);
  }

  @Override
  public WriteoffDto getWriteoffById(Long id) {
    if (writeoffRepository.existsById(id)) {
      return writeoffMapper.mapToDto(writeoffRepository.getOne(id));
    } else throw new NotFoundException(Invoice.class, "id", String.valueOf(id));
  }

  @Override
  public void deleteById(Long id) {
    if (writeoffRepository.existsById(id)) {
      if (writeoffRepository.getOne(id).getConfirmed()) {
        throw new IllegalStateException("You cant delete confirmed invoice");
      }
      writeoffRepository.deleteById(id);
    } else throw new NotFoundException(Invoice.class, "id", String.valueOf(id));
  }

  @Override
  @Transactional
  public void confirmWriteoff(Long writeoffId) {
      if (writeoffRepository.existsById(writeoffId)) {
          Writeoff writeoff = writeoffRepository.getOne(writeoffId);
          if (writeoff.getConfirmed()) {
              throw new IllegalStateException("Writeoff already confirmed");
          }
          List<ItemWarehouse> toDelete =
                  writeoff.getItems().stream()
                          .map(
                                  x ->
                                          ItemWarehouse.builder()
                                                  .id(
                                                          new ItemWarehousePk(
                                                                  x.getItem().getId(), writeoff.getWarehouse().getId()))
                                                  .warehouse(writeoff.getWarehouse())
                                                  .count(x.getCount())
                                                  .item(x.getItem())
                                                  .build())
                          .collect(Collectors.toList());
          warehouseService.removeAllItems(toDelete);
          writeoff.setConfirmed(true);
      } else throw new NotFoundException(Invoice.class, "id", String.valueOf(writeoffId));
  }

  @Override
  public List<WriteoffDto> getNotConfirmed() {
      return writeoffMapper.mapListDto(writeoffRepository.findByConfirmedFalse());
  }
}
