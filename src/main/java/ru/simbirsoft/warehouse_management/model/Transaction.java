package ru.simbirsoft.warehouse_management.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@Table(name = "transaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
            (cascade = CascadeType.ALL)
    private Item product;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
            (cascade = CascadeType.ALL)
    private Warehouse warehouse;



}
