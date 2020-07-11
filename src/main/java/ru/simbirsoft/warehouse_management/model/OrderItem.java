package ru.simbirsoft.warehouse_management.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "itemOrder")
public class OrderItem {
    @EmbeddedId
    private OrderItemPk id;

    @ManyToOne
    @MapsId("itemId")
    private Item item;

    @ManyToOne
    @MapsId("orderId")
    private Order order;

    @Column(name = "quantity")
    private Integer count;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        OrderItem that = (OrderItem) o;
        return Objects.equals(item, that.item) &&
                Objects.equals(order, that.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, order);
    }
}
