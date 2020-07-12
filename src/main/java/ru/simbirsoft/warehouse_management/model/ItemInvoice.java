package ru.simbirsoft.warehouse_management.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.warehouse_management.model.pk.ItemInvoicePk;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ItemInvoice implements Serializable {

    @EmbeddedId
    private ItemInvoicePk id;

    @ManyToOne
    @MapsId("item_id")
    private Item item;

    @ManyToOne
    @MapsId("invoice_id")
    private Invoice invoice;

    @Column (name = "quantity")
    private Integer count;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        ItemInvoice that = (ItemInvoice) o;
        return Objects.equals(item, that.item) &&
                Objects.equals(invoice, that.invoice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, invoice);
    }



}
