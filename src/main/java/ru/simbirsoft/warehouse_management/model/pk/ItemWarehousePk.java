package ru.simbirsoft.warehouse_management.model.pk;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ItemWarehousePk implements Serializable {
    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "warehouse_id")
    private Long warehouseId;

}
