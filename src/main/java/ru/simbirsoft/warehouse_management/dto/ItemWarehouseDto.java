package ru.simbirsoft.warehouse_management.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * ItemWarehouseDto is used to transfer data about Many-to-Many ItemWarehouse from Repository layer to Controller layer
 * Contains fields of ItemWarehouse Dto
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemWarehouseDto {
    @ApiModelProperty(
            name = "item"
    )
    private ItemDto item;
    @ApiModelProperty(
            name = "warehouse"
    )
    private WarehouseDto warehouse;
    @ApiModelProperty(
            name = "count",
            value = "count of the item in warehouse",
            dataType = "Integer",
            example = "42"
    )
    private Integer count;
}
