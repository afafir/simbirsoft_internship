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

    public static StockDto from (Stock stock){
        return StockDto.builder()
                .id(stock.getId())
                .item(ItemDto.from(stock.getItem()))
                .quantity(stock.getQuantity())
                .warehouse(WarehouseDto.from(stock.getWarehouse()))
                .build();

    }

    public static List<StockDto> from (List<Stock> stocks){
        return stocks.stream().map(StockDto::from).collect(Collectors.toList());
    }



}
