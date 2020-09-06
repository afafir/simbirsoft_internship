package ru.simbirsoft.warehouse_management.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.LazyCollection;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
@Entity
@Table(name = "item")
public class Item {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Column(name = "item_code")
  private String code;

  @ManyToMany
  @JoinTable(
          name = "item_category",
          joinColumns = @JoinColumn(name = "item_id"),
          inverseJoinColumns = @JoinColumn(name = "category_id")
  )
  private List<Category> categories;

  @Column(name = "price")
  private float price;

  @Column(name = "description")
  private String description;
}
