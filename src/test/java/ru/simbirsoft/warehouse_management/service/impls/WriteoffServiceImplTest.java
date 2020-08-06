package ru.simbirsoft.warehouse_management.service.impls;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.simbirsoft.warehouse_management.dto.WriteoffDto;
import ru.simbirsoft.warehouse_management.dto.createForms.WriteoffCreateDto;
import ru.simbirsoft.warehouse_management.exception.NotFoundException;
import ru.simbirsoft.warehouse_management.model.ItemWarehouse;
import ru.simbirsoft.warehouse_management.model.Writeoff;
import ru.simbirsoft.warehouse_management.model.pk.ItemWarehousePk;
import ru.simbirsoft.warehouse_management.repository.ItemWarehouseRepository;
import ru.simbirsoft.warehouse_management.repository.WriteoffRepository;
import ru.simbirsoft.warehouse_management.service.WriteoffService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql({"/sql/dropTable.sql", "/sql/table.sql", "/sql/dataTest.sql"})
class WriteoffServiceImplTest {

  @Autowired private WriteoffService writeoffService;

  @Autowired private WriteoffRepository writeoffRepository;

  @Autowired private ItemWarehouseRepository itemWarehouseRepository;

  @Test
  @Transactional
  void createWriteoff() {
    WriteoffCreateDto writeoffCreateDto =
        WriteoffCreateDto.builder()
            .itemCount(Map.of(1L, 1, 2L, 1))
            .localDateTime(LocalDateTime.now())
            .warehouseId(1L)
            .build();
    WriteoffDto writeoffDto = writeoffService.createWriteoff(writeoffCreateDto);
    assertFalse(writeoffDto.getConfirmed());
    assertEquals(1L, writeoffDto.getWarehouse().getId());
  }

  @Test
  @Transactional
  void createWriteoffWithWarehouseWhichDoesntExist() {
    WriteoffCreateDto writeoffCreateDto =
        WriteoffCreateDto.builder()
            .itemCount(Map.of(1L, 1, 2L, 1))
            .localDateTime(LocalDateTime.now())
            .warehouseId(1123L)
            .build();
    assertThrows(NotFoundException.class, () -> writeoffService.createWriteoff(writeoffCreateDto));
  }

  @Test
  @Transactional
  void createWriteoffWithItemWhichDoesntExist() {
    WriteoffCreateDto writeoffCreateDto =
        WriteoffCreateDto.builder()
            .itemCount(Map.of(11212231L, 1, 2L, 1))
            .localDateTime(LocalDateTime.now())
            .warehouseId(1L)
            .build();
    assertThrows(NotFoundException.class, () -> writeoffService.createWriteoff(writeoffCreateDto));
  }

  @Test
  @Transactional
  void getWriteoffById() {
    WriteoffDto writeoffDto = writeoffService.getWriteoffById(1L);
    assertEquals(true, writeoffDto.getConfirmed());
    assertEquals(1L, writeoffDto.getId());
  }

  @Test
  void getWriteoffThatDoesntExist() {
    assertThrows(NotFoundException.class, () -> writeoffService.getWriteoffById(123123L));
  }

  @Test
  @Transactional
  void deleteById() {
    writeoffService.deleteById(2L);
    assertFalse(writeoffRepository.findById(2L).isPresent());
  }

  @Test
  @Transactional
  void deleteByIdThatAlreadyConfirmed() {
    assertThrows(IllegalStateException.class, ()->writeoffService.confirmWriteoff(1L));
  }

  @Test
  @Transactional
  void confirmWriteoff() {
    writeoffService.confirmWriteoff(2L);
    Writeoff writeoff = writeoffRepository.getOne(2L);
    assertTrue(writeoff.getConfirmed());
    // проверяем, изменилось ли количество вещей на складе
    ItemWarehouse itemWarehouse = itemWarehouseRepository.getOne(new ItemWarehousePk(2L, 1L));
    assertEquals(itemWarehouse.getCount(), 83);
  }

  @Test
  @Transactional
  void confirmWriteoffThatAlreadyConfirmed() {
    assertThrows(IllegalStateException.class, () -> writeoffService.confirmWriteoff(1L));
  }

  @Test
  @Transactional
  void confirmWriteoffThatDoesntExist() {
    assertThrows(NotFoundException.class, () -> writeoffService.confirmWriteoff(1123L));
  }

  @Test
  @Transactional
  void getNotConfirmed() {
    List<WriteoffDto> writeoffDtos = writeoffService.getNotConfirmed();
    for (WriteoffDto writeoffDto : writeoffDtos) {
      assertFalse(writeoffDto.getConfirmed());
    }
  }
}
