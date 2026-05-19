package org.shop;

import org.shop.data.*;
import org.shop.exception.GoodNotFoundException;
import org.shop.exception.InsufficientQuantityException;
import org.shop.service.impl.CashDeskServiceImpl;
import org.shop.service.impl.StoreServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Store store = new Store();
        StoreServiceImpl storeService = new StoreServiceImpl();

        Cashier cashier1 = new Cashier("Ivan", BigDecimal.valueOf(1000));
        CashDesk cashDesk1 = new CashDesk(cashier1);
        CashDeskServiceImpl cashDeskService = new CashDeskServiceImpl(store);

        Good g1 = new Good("Milk", BigDecimal.valueOf(2.55), GoodCategories.FOOD, LocalDate.parse("2026-05-30"), 10);
        Good g2 = new Good("Bread", BigDecimal.valueOf(1.78), GoodCategories.FOOD, LocalDate.parse("2026-05-18"), 35);
        storeService.deliveryGood(store, g1);
        storeService.deliveryGood(store, g2);

        try {
            cashDeskService.scanGood(cashDesk1,1, 1);
            cashDeskService.scanGood(cashDesk1,2, 2);
            cashDeskService.scanGood(cashDesk1,2, 2);
            storeService.soldGoods(store, cashDesk1.getCurrCart());
            cashDesk1.getCurrCart().clear();
            System.out.println(store.getDeliveredGoods());
            System.out.println(store.getSoldGoods());

            cashDeskService.scanGood(cashDesk1, 2, 2);
            storeService.soldGoods(store, cashDesk1.getCurrCart());
            cashDesk1.getCurrCart().clear();
            System.out.println(store.getSoldGoods());
        } catch (GoodNotFoundException | InsufficientQuantityException e) {
            System.out.println(e.getMessage());
        }

//        Scanner sc = new Scanner(System.in);
//        GoodServiceImpl goodService = new GoodServiceImpl();

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