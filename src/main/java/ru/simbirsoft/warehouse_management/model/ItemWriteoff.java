package ru.simbirsoft.warehouse_management.model;

import lombok.*;
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
@EqualsAndHashCode
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


}
