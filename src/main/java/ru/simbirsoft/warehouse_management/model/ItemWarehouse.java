package ru.simbirsoft.warehouse_management.model;

import lombok.*;
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
@EqualsAndHashCode
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

}
