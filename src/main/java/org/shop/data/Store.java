package org.shop.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Store {
    private final List<Good> deliveredGoods;
    private final Map<Good, Integer> soldGoods;

    public Store() {
        this.deliveredGoods = new ArrayList<>();
        this.soldGoods = new HashMap<>();
    }

    public List<Good> getDeliveredGoods() {
        return deliveredGoods;
    }

    public Map<Good, Integer> getSoldGoods() {
        return soldGoods;
    }

    @Override
    public String toString() {
        return "Store{" +
                "deliveredGoods=" + deliveredGoods +
                ", soldGoods=" + soldGoods +
                '}';
    }
}
