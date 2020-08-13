package ru.simbirsoft.warehouse_management.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "warehouse")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Warehouse {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "address", nullable = false)
  private String address;

  @OneToMany(mappedBy = "warehouse",cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ItemWarehouse> items;
}
