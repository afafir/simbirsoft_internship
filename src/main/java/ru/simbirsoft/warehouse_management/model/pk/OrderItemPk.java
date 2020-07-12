package ru.simbirsoft.warehouse_management.model.pk;

import lombok.AllArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
@Embeddable
@AllArgsConstructor

public class OrderItemPk implements Serializable {

    @Column(name = "item_id")
    private Long itemId;

    @Column (name = "order_id")
    private Long orderId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        OrderItemPk that = (OrderItemPk) o;
        return Objects.equals(itemId, that.itemId) &&
                Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, orderId);
    }

}
