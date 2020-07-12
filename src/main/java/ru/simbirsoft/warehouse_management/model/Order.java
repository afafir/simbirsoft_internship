package ru.simbirsoft.warehouse_management.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.warehouse_management.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @ManyToOne(
            cascade = CascadeType.ALL)
    private User customer;

    @Column(name = "isConfirmed")
    private Boolean isConfirmed;

    @Column(name = "orderedAt")
    private LocalDateTime orderedAt;
    @ManyToOne
    private Shop shop;

}
