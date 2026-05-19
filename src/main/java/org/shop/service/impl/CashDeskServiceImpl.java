package org.shop.service.impl;

import org.shop.data.CashDesk;
import org.shop.data.Good;
import org.shop.data.Store;
import org.shop.exception.GoodNotFoundException;
import org.shop.exception.InsufficientQuantityException;
import org.shop.service.CashDeskService;

public class CashDeskServiceImpl implements CashDeskService {
    private final Store store;

    public CashDeskServiceImpl(Store store) {
        this.store = store;
    }

    @Override
    public void scanGood(CashDesk cashDesk, int id, int quantity) throws GoodNotFoundException, InsufficientQuantityException {
        Good good = findGoodById(id);

        if (good.getQuantity() < quantity) {
            throw new InsufficientQuantityException(good, quantity);
        }

        int goodQuantity = cashDesk.getCurrCart().getOrDefault(good, 0);
        cashDesk.getCurrCart().put(good, goodQuantity + quantity);
    }

    @Override
    public void emptyCart(CashDesk cashDesk) {
        cashDesk.getCurrCart().clear();
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
