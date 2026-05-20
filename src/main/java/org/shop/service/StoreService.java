package org.shop.service;

import org.shop.data.CashDesk;
import org.shop.data.Store;
import org.shop.data.Good;
import org.shop.exception.GoodNotFoundException;

import java.util.Map;

public interface StoreService {
    void deliveryGood(Store store, Good good);
    void soldGoods(Store store, Map<Good, Integer> cart);
    Good findGoodById(Store store, int id) throws GoodNotFoundException;
}
