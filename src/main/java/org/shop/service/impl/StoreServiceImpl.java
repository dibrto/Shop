package org.shop.service.impl;

import org.shop.exception.GoodNotFoundException;
import org.shop.data.Good;
import org.shop.data.Store;
import org.shop.exception.InsufficientQuantityException;
import org.shop.service.StoreService;

import java.util.Map;

public class StoreServiceImpl implements StoreService {
    private final Store store;

    public StoreServiceImpl(Store store) {
        this.store = store;
    }

    @Override
    public void deliveryGood(Good good) {
        store.getDeliveredGoods().add(good);
        store.getSoldGoods().put(good, 0);
    }

    @Override
    public void addToCart(int id, int quantity) throws GoodNotFoundException, InsufficientQuantityException {
        Good good = findGoodById(id);

        if (good.getQuantity() < quantity) {
            throw new InsufficientQuantityException(good, quantity);
        }

        int goodQuantity = store.getCart().getOrDefault(good, 0);
        store.getCart().put(good, goodQuantity + quantity);
    }

    @Override
    public void soldGoods() {
        for(Map.Entry<Good, Integer> entry : store.getCart().entrySet()) {
            Good good = entry.getKey();
            int quantity = entry.getValue();

            good.setQuantity(good.getQuantity() - quantity);

            int goodQuantity = store.getSoldGoods().getOrDefault(good, 0);
            store.getSoldGoods().put(good, goodQuantity + quantity);
        }

        store.getCart().clear();
    }

    private Good findGoodById(int id) throws GoodNotFoundException {
        for (Good good : store.getDeliveredGoods()) {
            if (good.getId() == id) {
                return good;
            }
        }

        throw new GoodNotFoundException(id);
    }
}
