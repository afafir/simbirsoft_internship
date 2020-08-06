package ru.simbirsoft.warehouse_management.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ItemWriteoffDto is used to transfer data about Many-to-Many ItemWriteoff from Repository layer to Controller layer
 * Contains fields of ItemWriteoff Dto
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemWriteoffDto {
    @ApiModelProperty(
            name = "item"
    )
    private ItemDto item;
    @ApiModelProperty(
            name = "writeoff"
    )
    private WriteoffDto writeoffDto;
    @ApiModelProperty(
            name = "count",
            value = "count of the item in writeoff",
            dataType = "Integer",
            example = "42"
    )
    private Integer count;
}
