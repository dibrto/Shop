package org.shop.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CashDesk {
    private final UUID id;
    private final Cashier cashier;
    private final Map<Good, Integer> currCart;


    public CashDesk(Cashier cashier) {
        this.id = UUID.randomUUID();
        this.cashier = cashier;
        this.currCart = new HashMap<>();
    }

    public UUID getId() {
        return id;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public Map<Good, Integer> getCurrCart() {
        return currCart;
    }

    @Override
    public String toString() {
        return "CashDesk{" +
                "id=" + id +
                ", cashier=" + cashier +
                ", currCart=" + currCart +
                '}';
    }
}
