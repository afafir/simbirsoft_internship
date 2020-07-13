package ru.simbirsoft.warehouse_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.warehouse_management.model.Category;
import ru.simbirsoft.warehouse_management.model.Item;
import ru.simbirsoft.warehouse_management.model.enums.Dimension;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDto {

    private String code;
    private List<CategoryDto> category;
    private float price;
    private String description;
    private String dimension;


    public static ItemDto from(Item item){
        return ItemDto.builder()
                .category(CategoryDto.from(item.getCategory()))
                .price(item.getPrice())
                .dimension(item.getDimension().toString())
                .description(item.getDescription())
                .build();
    }

    public static List<ItemDto> from (List<Item> items){
        return items.stream().map(ItemDto::from).collect(Collectors.toList());
    }

}
