package ru.simbirsoft.warehouse_management.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.warehouse_management.model.pk.ItemWriteoffPk;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "item_writeoff")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ItemWriteoff implements Serializable {
  @EmbeddedId private ItemWriteoffPk id;

  @ManyToOne
  @MapsId("item_id")
  private Item item;

  @ManyToOne
  @MapsId("writeoff_id")
  private Writeoff writeoff;

  @Column(name = "quantity")
  private Integer count;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    ItemWriteoff that = (ItemWriteoff) o;
    return Objects.equals(item, that.item) && Objects.equals(writeoff, that.writeoff);
  }

  @Override
  public int hashCode() {
    return Objects.hash(item, writeoff);
  }
}
