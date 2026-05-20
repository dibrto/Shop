package org.shop.service;

import org.shop.data.*;
import org.shop.exception.GoodNotFoundException;

import java.math.BigDecimal;
import java.util.Map;

public interface StoreService {
    void deliveryGood(Store store, Good good);
    void soldGoods(Store store, Map<Good, Integer> cart);
    Good findGoodById(Store store, int id) throws GoodNotFoundException;

    void addReceipt(Store store, Receipt receipt);
    void addCashier(Store store, Cashier cashier);

    BigDecimal getRevenue(Store store);
    BigDecimal getExpenses(Store store);
    BigDecimal getProfit(Store store);
}
