package org.shop.service;

import org.shop.data.*;
import org.shop.exception.GoodNotFoundException;

import java.util.Map;

public interface StoreService {
    void deliveryGood(Store store, Good good);
    void soldGoods(Store store, Map<Good, Integer> cart);
    Good findGoodById(Store store, int id) throws GoodNotFoundException;

    void addReceipt(Store store, Receipt receipt);
    void addCashier(Store store, Cashier cashier);
}
