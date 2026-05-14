package org.shop.service;

import org.shop.exception.GoodNotFoundException;
import org.shop.data.Good;
import org.shop.exception.InsufficientQuantityException;

public interface StoreService {
    void deliveryGood(Good good);
    void addToCart(int id, int quantity) throws GoodNotFoundException, InsufficientQuantityException;
    void soldGoods();
}
