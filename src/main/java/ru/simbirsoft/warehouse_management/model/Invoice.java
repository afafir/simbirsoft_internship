package ru.simbirsoft.warehouse_management.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "invoice")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Invoice {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne private Supplier supplier;

  @OneToMany(mappedBy = "invoice",cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ItemInvoice> items;

  @Column(name = "arrived_at")
  private LocalDateTime arrivedAt;

  @ManyToOne private Warehouse warehouse;

  @Column(name = "is_confirmed")
  private Boolean confirmed;
}
