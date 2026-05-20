package org.shop.service.impl;

import org.shop.data.CashDesk;
import org.shop.data.Good;
import org.shop.exception.InsufficientQuantityException;
import org.shop.service.CashDeskService;

import java.math.BigDecimal;
import java.util.Map;

public class CashDeskServiceImpl implements CashDeskService {
    private final GoodServiceImpl goodService;

    public CashDeskServiceImpl(GoodServiceImpl goodService) {
        this.goodService = goodService;
    }

    @Override
    public void addToCart(CashDesk cashDesk, Good good, int quantity) throws InsufficientQuantityException {
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

    @Override
    public BigDecimal getCartPrice(CashDesk cashDesk) {
        BigDecimal totalPrice = BigDecimal.ZERO;

        for(Map.Entry<Good, Integer> entry : cashDesk.getCurrCart().entrySet()) {
            Good good = entry.getKey();
            int quantity = entry.getValue();

            totalPrice = totalPrice.add(goodService.getSalePrice(good).multiply(BigDecimal.valueOf(quantity)));
        }

        return totalPrice;
    }
}
