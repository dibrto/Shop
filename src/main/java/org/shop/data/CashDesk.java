package org.shop.data;

import java.util.UUID;

public class CashDesk {
    private final UUID id;
    private final Cashier cashier;

    public CashDesk(Cashier cashier) {
        this.id = UUID.randomUUID();
        this.cashier = cashier;
    }

    public UUID getId() {
        return id;
    }

    public Cashier getCashier() {
        return cashier;
    }

    @Override
    public String toString() {
        return "CashDesk{" +
                "id=" + id +
                ", cashier=" + cashier +
                '}';
    }
}
