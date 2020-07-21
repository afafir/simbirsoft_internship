package ru.simbirsoft.warehouse_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.warehouse_management.model.Item;
import ru.simbirsoft.warehouse_management.model.Stock;
import ru.simbirsoft.warehouse_management.model.Warehouse;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockDto {

  private Long id;
  private ItemDto item;
  private int quantity;
  private WarehouseDto warehouse;
}
