package org.shop.service.impl;

import org.shop.data.*;
import org.shop.exception.GoodNotFoundException;
import org.shop.service.StoreService;

import java.math.BigDecimal;
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

    @Override
    public void addCashier(Store store, Cashier cashier) {
        store.getCashiers().add(cashier);
    }

    private BigDecimal calcSalariesExpenses(Store store){
        BigDecimal total = BigDecimal.ZERO;

        for(Cashier cashier : store.getCashiers()){
            total = total.add(cashier.getSalary());
        }

        return total;
    }

    private BigDecimal calcDeliveryExpenses(Store store){
        BigDecimal total = BigDecimal.ZERO;

        for(Map.Entry<Good, Integer> entry : store.getSoldGoods().entrySet()) {
            Good good = entry.getKey();
            int soldNum = entry.getValue();

            int totalQuantity = good.getQuantity() + soldNum;
            BigDecimal goodDeliveryPrice = good.getDeliveryPrice().multiply(BigDecimal.valueOf(totalQuantity));
            total = total.add(goodDeliveryPrice);
        }

        return total;
    }

    @Override
    public BigDecimal getExpenses(Store store) {
        BigDecimal totalSalary = calcSalariesExpenses(store);
        BigDecimal totalDelivery = calcDeliveryExpenses(store);

        return totalSalary.add(totalDelivery);
    }

    @Override
    public BigDecimal getRevenue(Store store) {
        BigDecimal total =  BigDecimal.ZERO;

       for (Receipt receipt : store.getReceipts()) {
           total = total.add(receipt.getTotalSum());
       }

       return total;
    }


    @Override
    public BigDecimal getProfit(Store store) {
        return getRevenue(store).subtract(getExpenses(store));
    }
}
