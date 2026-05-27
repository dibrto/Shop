package org.shop;

import org.shop.data.*;
import org.shop.exception.GoodNotFoundException;
import org.shop.exception.InsufficientQuantityException;
import org.shop.service.impl.CashDeskServiceImpl;
import org.shop.service.impl.GoodServiceImpl;
import org.shop.service.impl.StoreServiceImpl;
import org.shop.utility.Serializer;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Store store = new Store();
        GoodServiceImpl goodService = new GoodServiceImpl();
        StoreServiceImpl storeService = new StoreServiceImpl();
        CashDeskServiceImpl cashDeskService = new CashDeskServiceImpl(goodService);

        // create receipts directory if it doesn't exist
        File directory = new File("receipts");
        if (!directory.exists() && !directory.mkdir()) {
            try {
                throw new IOException("Failed to create directory receipts");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        // add data
        Cashier cashier1 = new Cashier("Ivan", BigDecimal.valueOf(500));
        CashDesk cashDesk1 = new CashDesk(cashier1);
        storeService.addCashier(store, cashier1);

        Cashier cashier2 = new Cashier("Peter", BigDecimal.valueOf(1000));
        CashDesk cashDesk2 = new CashDesk(cashier2);
        storeService.addCashier(store, cashier2);

        Good g1 = new Good("Milk", BigDecimal.valueOf(1.2), GoodCategories.FOOD, LocalDate.parse("2026-06-30"), 100);
        Good g2 = new Good("Bread", BigDecimal.valueOf(1.5), GoodCategories.FOOD, LocalDate.parse("2026-06-18"), 10000);
        Good g3 = new Good("TV Samsung", BigDecimal.valueOf(1500), GoodCategories.NON_FOOD, LocalDate.parse("2026-06-30"), 100);
        storeService.deliveryGood(store, g1);
        storeService.deliveryGood(store, g2);
        storeService.deliveryGood(store, g3);

        Good good;
        // sale 1
        try {
            good = storeService.findGoodById(store, 1);
            cashDeskService.addToCart(cashDesk1, good, 2);

            good = storeService.findGoodById(store, 2);
            cashDeskService.addToCart(cashDesk1, good, 2);

            BigDecimal totalSum = cashDeskService.getCartPrice(cashDesk1);
            if (totalSum.compareTo(BigDecimal.valueOf(10)) > 0) {
                System.out.println("Not enough money");
            }

            Receipt receipt = new Receipt(cashier1, new ArrayList<>(cashDesk1.getCurrCart().keySet()), totalSum);

            String filename = "Receipt_" + receipt.getReceiptNo() + ".ser";
            Serializer.serialize("receipts/" + filename, receipt);

            storeService.addReceipt(store, receipt);
            storeService.soldGoods(store, cashDesk1.getCurrCart());
            cashDeskService.emptyCart(cashDesk1);
        } catch (GoodNotFoundException | InsufficientQuantityException | IllegalStateException | IOException e){
            System.out.println(e.getMessage());
        }

        // sale 2
        try {
            good = storeService.findGoodById(store, 3);
            cashDeskService.addToCart(cashDesk2, good, 100);

            BigDecimal totalSum = cashDeskService.getCartPrice(cashDesk2);
            if (totalSum.compareTo(BigDecimal.valueOf(200000)) > 0) {
                System.out.println("Not enough money");
            }

            Receipt receipt = new Receipt(cashier2, new ArrayList<>(cashDesk2.getCurrCart().keySet()), totalSum);

            String filename = "Receipt_" + receipt.getReceiptNo() + ".ser";
            Serializer.serialize("receipts/" + filename, receipt);

            storeService.addReceipt(store, receipt);
            storeService.soldGoods(store, cashDesk2.getCurrCart());
            cashDeskService.emptyCart(cashDesk2);
        } catch (GoodNotFoundException | InsufficientQuantityException | IllegalStateException | IOException e){
            System.out.println(e.getMessage());
        }

        // deserialize receipt
        try {
            System.out.println("Receipt_0000000001.ser");
            System.out.println(Serializer.deserialize("receipts/Receipt_0000000001.ser", Receipt.class));
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        System.out.println();
        System.out.println("Store financies");
        System.out.println("Expenses: " + storeService.getExpenses(store));
        System.out.println("Revenue: " + storeService.getRevenue(store));
        System.out.println("Profit: " + storeService.getProfit(store));
    }
}