package ru.simbirsoft.warehouse_management.model;

import lombok.AllArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@AllArgsConstructor
public class ItemInvoicePk implements Serializable {

    @Column(name = "itemId")
    private Long itemId;

    @Column (name = "invoiceId")
    private Long invoiceId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ItemInvoicePk that = (ItemInvoicePk) o;
        return Objects.equals(itemId, that.itemId) &&
                Objects.equals(invoiceId, that.invoiceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, invoiceId);
    }



}
