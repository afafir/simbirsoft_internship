package ru.simbirsoft.warehouse_management.model.pk;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ItemWriteoffPk implements Serializable {
    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "writeoff_id")
    private Long writeoffId;

}
