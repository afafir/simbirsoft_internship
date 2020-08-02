package ru.simbirsoft.warehouse_management.model.pk;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class ItemWriteoffPk implements Serializable {
    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "writeoff_id")
    private Long writeoffId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemWriteoffPk that = (ItemWriteoffPk) o;
        return Objects.equals(itemId, that.itemId) && Objects.equals(writeoffId, that.writeoffId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, writeoffId);
    }
}
