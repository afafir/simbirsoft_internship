package ru.simbirsoft.warehouse_management.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.warehouse_management.model.pk.OrderItemPk;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "item_order")
public class OrderItem implements Serializable {
  @EmbeddedId private OrderItemPk id;

  @ManyToOne
  @MapsId("item_id")
  private Item item;

  @ManyToOne
  @MapsId("order_id")
  private Order order;

  @Column(name = "quantity")
  private Integer count;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    OrderItem that = (OrderItem) o;
    return Objects.equals(((OrderItem) o).getId(), that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(item, order);
  }
}
