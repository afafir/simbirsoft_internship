package ru.simbirsoft.warehouse_management.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "shop")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Shop {

  @Id @GeneratedValue private Long id;

  @Column(name = "name")
  private String name;

  @ManyToOne private Warehouse warehouse;

  @OneToMany
  @JoinColumn(name = "shop_id")
  private List<Order> orders;
}
