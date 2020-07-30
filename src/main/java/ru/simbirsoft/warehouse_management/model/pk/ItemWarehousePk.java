package ru.simbirsoft.warehouse_management.model.pk;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class ItemWarehousePk implements Serializable {
    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "warehouse_id")
    private Long warehouseId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemWarehousePk that = (ItemWarehousePk) o;
        return Objects.equals(itemId, that.itemId) && Objects.equals(warehouseId, that.warehouseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, warehouseId);
    }
}
