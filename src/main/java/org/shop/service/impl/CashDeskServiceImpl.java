package org.shop.service.impl;

import org.shop.data.CashDesk;
import org.shop.data.Good;
import org.shop.exception.InsufficientQuantityException;
import org.shop.service.CashDeskService;

public class CashDeskServiceImpl implements CashDeskService {
    @Override
    public void scanGood(CashDesk cashDesk, Good good, int quantity) throws InsufficientQuantityException {
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


}
