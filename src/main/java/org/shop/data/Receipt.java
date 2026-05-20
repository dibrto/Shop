package org.shop.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Receipt implements Serializable {
    private static int nextNo = 1;

    private final UUID id;
    private final String ReceiptNo;
    private final LocalDate ReceiptDate;
    private final Cashier cashier;
    private final List<Good> goods;
    private final BigDecimal totalSum;

    public Receipt(Cashier cashier, List<Good> goods, BigDecimal totalSum) {
        this.id = UUID.randomUUID();
        this.ReceiptNo = String.format("%010d", nextNo++);
        this.ReceiptDate = LocalDate.now();
        this.cashier = cashier;
        this.goods = goods;
        this.totalSum = totalSum;
    }

    public UUID getId() {
        return id;
    }

    public String getReceiptNo() {
        return ReceiptNo;
    }

    public LocalDate getReceiptDate() {
        return ReceiptDate;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public List<Good> getGoods() {
        return goods;
    }

    public BigDecimal getTotalSum() {
        return totalSum;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "id=" + id +
                ", ReceiptNo='" + ReceiptNo + '\'' +
                ", ReceiptDate=" + ReceiptDate +
                ", cashier=" + cashier +
                ", goods=" + goods +
                ", totalSum=" + totalSum +
                '}';
    }
}
