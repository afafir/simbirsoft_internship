package ru.simbirsoft.warehouse_management.model;

import lombok.*;
import ru.simbirsoft.warehouse_management.model.pk.ItemInvoicePk;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "item_invoice")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode

public class ItemInvoice implements Serializable {

  @EmbeddedId private ItemInvoicePk id;

  @ManyToOne
  @MapsId("item_id")
  private Item item;

  @ManyToOne
  @MapsId("invoice_id")
  private Invoice invoice;

  @Column(name = "quantity")
  private Integer count;

}
