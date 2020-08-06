package ru.simbirsoft.warehouse_management.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "writeoff")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Writeoff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "writeoff",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemWriteoff> items;

    @Column(name = "time")
    private LocalDateTime time;

    @ManyToOne private Warehouse warehouse;

    @Column(name = "is_confirmed")
    private Boolean confirmed;
}
