package org.shop.service.impl;

import org.shop.data.CashDesk;
import org.shop.data.Good;
import org.shop.data.Receipt;
import org.shop.data.Store;
import org.shop.exception.GoodNotFoundException;
import org.shop.service.CashDeskService;
import org.shop.service.StoreService;

import java.util.Map;

public class StoreServiceImpl implements StoreService {
    @Override
    public void deliveryGood(Store store, Good good) {
        store.getDeliveredGoods().add(good);
        store.getSoldGoods().put(good, 0);
    }

    @Override
    public void soldGoods(Store store, Map<Good, Integer> cart) {
        for(Map.Entry<Good, Integer> entry : cart.entrySet()) {
            Good good = entry.getKey();
            int quantity = entry.getValue();

            good.setQuantity(good.getQuantity() - quantity);

            int goodQuantity = store.getSoldGoods().getOrDefault(good, 0);
            store.getSoldGoods().put(good, goodQuantity + quantity);
        }
    }

    @Override
    public Good findGoodById(Store store, int id) throws GoodNotFoundException {
        for (Good good : store.getDeliveredGoods()) {
            if (good.getId() == id) {
                return good;
            }
        }

        throw new GoodNotFoundException(id);
    }

    @Override
    public void addReceipt(Store store, Receipt receipt) {
        store.getReceipts().add(receipt);
    }
}
