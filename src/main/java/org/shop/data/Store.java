package org.shop.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Store {
    private final List<Good> deliveredGoods;
    private final Map<Good, Integer> soldGoods;
    private final Map<Good, Integer> cart;

    public Store() {
        this.deliveredGoods = new ArrayList<>();
        this.soldGoods = new HashMap<>();
        this.cart = new HashMap<>();
    }

    public List<Good> getDeliveredGoods() {
        return deliveredGoods;
    }

    public Map<Good, Integer> getSoldGoods() {
        return soldGoods;
    }

    public Map<Good, Integer> getCart() {
        return cart;
    }

    @Override
    public String toString() {
        return "Store{" +
                "deliveredGoods=" + deliveredGoods +
                ", soldGoods=" + soldGoods +
                ", cart=" + cart +
                '}';
    }
}
