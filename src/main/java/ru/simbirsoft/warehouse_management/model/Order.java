package ru.simbirsoft.warehouse_management.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.warehouse_management.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "db_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(cascade = CascadeType.MERGE)
  private User customer;

  @Column(name = "is_confirmed")
  private Boolean isConfirmed;

  @OneToMany(mappedBy = "order", orphanRemoval = true)
  private List<OrderItem> items;

  @Column(name = "ordered_at")
  private LocalDateTime orderedAt;

  @ManyToOne private Shop shop;

  @Transient private float cost;

  @PostLoad
  public void totalPrice() {
    float cost = 0;
    for (OrderItem orderItem : items) {
      cost += orderItem.getCount() * orderItem.getItem().getPrice();
    }
    this.cost = cost;
  }
}
