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


        Cashier cashier1 = new Cashier("Ivan", BigDecimal.valueOf(10));
        CashDesk cashDesk1 = new CashDesk(cashier1);
        storeService.addCashier(store, cashier1);

        Cashier cashier2 = new Cashier("Peter", BigDecimal.valueOf(10));
        CashDesk cashDesk2 = new CashDesk(cashier2);
        storeService.addCashier(store, cashier2);

        Good g1 = new Good("Milk", BigDecimal.valueOf(2), GoodCategories.FOOD, LocalDate.parse("2026-06-30"), 10);
        Good g2 = new Good("Bread", BigDecimal.valueOf(2), GoodCategories.FOOD, LocalDate.parse("2026-06-18"), 100);
        storeService.deliveryGood(store, g1);
        storeService.deliveryGood(store, g2);

        Good good;
        try {
            good = storeService.findGoodById(store, 1);
            cashDeskService.addToCart(cashDesk1, good, 1);

            good = storeService.findGoodById(store, 2);
            cashDeskService.addToCart(cashDesk1, good, 100);

            BigDecimal totalSum = cashDeskService.getCartPrice(cashDesk1);
            if (totalSum.compareTo(BigDecimal.valueOf(2000)) > 0) {
                System.out.println("Not enough money");
            }

            Receipt receipt = new Receipt(cashier1, new ArrayList<>(cashDesk1.getCurrCart().keySet()), totalSum);

            File directory = new File("receipts");
            if (!directory.exists() && !directory.mkdir()) {
                throw new IOException("Failed to create directory");
            }
            String filename = "Receipt_" + receipt.getReceiptNo() + ".ser";
            Serializer.serialize("receipts/" + filename, receipt);

            storeService.addReceipt(store, receipt);
            storeService.soldGoods(store, cashDesk1.getCurrCart());
            cashDeskService.emptyCart(cashDesk1);

            System.out.println(Serializer.deserialize("receipt/Receipt_0000000001.ser", Receipt.class));
//            System.out.println(store);
        } catch (GoodNotFoundException | InsufficientQuantityException | IllegalStateException | IOException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        System.out.println(storeService.getExpenses(store));
        System.out.println(storeService.getRevenue(store));
        System.out.println(storeService.getProfit(store));

//        Scanner sc = new Scanner(System.in);

//        try {
//            System.out.println("Name: ");
//            String name = sc.nextLine();
//
//            System.out.println("Delivery price: ");
//            BigDecimal price = new BigDecimal(sc.nextLine());
//
//            System.out.println("Category (FOOD, NON_FOOD): ");
//            GoodCategories category = GoodCategories.valueOf(sc.nextLine().toUpperCase());
//
//            System.out.println("Date (yyyy-MM-dd): ");
//            LocalDate date = LocalDate.parse(sc.nextLine());
//
//            System.out.println("Quantity: ");
//            int quantity = Integer.parseInt(sc.nextLine());
//
//            Good good = new Good(name, price, category, date, quantity);
//            System.out.println(goodService.getSalePrice(good));
//        } catch (IllegalArgumentException | IllegalStateException e) {
//            System.out.println(e.getMessage());
//        }
    }
}