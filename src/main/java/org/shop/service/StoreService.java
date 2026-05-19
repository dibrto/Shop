package org.shop.service;

import org.shop.data.Store;
import org.shop.data.Good;

import java.util.Map;

public interface StoreService {
    void deliveryGood(Store store, Good good);
    void soldGoods(Store store, Map<Good, Integer> cart);
}
