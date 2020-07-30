package ru.simbirsoft.warehouse_management.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.warehouse_management.model.pk.ItemWarehousePk;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "item_warehouse")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ItemWarehouse implements Serializable {
    @EmbeddedId
    private ItemWarehousePk id;

    @ManyToOne
    @MapsId("item_id")
    private Item item;

    @ManyToOne
    @MapsId("warehouse_id")
    private Warehouse warehouse;

    @Column(name = "quantity")
    private Integer count;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ItemWarehouse that = (ItemWarehouse) o;
        return Objects.equals(item, that.item) && Objects.equals(warehouse, that.warehouse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, warehouse);
    }
}
