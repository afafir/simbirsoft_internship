package ru.simbirsoft.warehouse_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.warehouse_management.model.Item;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDto {
    private Long id;
    private String code;
    private List<CategoryDto> category;
    private float price;
    private String description;
    private String name;


    public static ItemDto from(Item item){
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .code(item.getCode())
                .category(CategoryDto.from(item.getCategory()))
                .price(item.getPrice())
                .description(item.getDescription())
                .build();
    }

    public static List<ItemDto> from (List<Item> items){
        return items.stream().map(ItemDto::from).collect(Collectors.toList());
    }

}
