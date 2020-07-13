package ru.simbirsoft.warehouse_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.warehouse_management.model.ItemInvoice;
import ru.simbirsoft.warehouse_management.model.OrderItem;

import java.util.List;
import java.util.stream.Collectors;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemInvoiceDto {
    private ItemDto itemDto;
    private int quantity;

    public static ItemInvoiceDto from(ItemInvoice itemInvoice){
        return ItemInvoiceDto.builder()
                .itemDto(ItemDto.from(itemInvoice.getItem()))
                .quantity(itemInvoice.getCount())
                .build();
    }

    public static List<ItemInvoiceDto> from(List<ItemInvoice> itemInvoices){
        return itemInvoices.stream().map(ItemInvoiceDto::from).collect(Collectors.toList());
    }
}
