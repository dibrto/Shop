package org.shop;

import org.shop.data.Goods;
import org.shop.data.GoodsCategories;
import org.shop.service.impl.GoodServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GoodServiceImpl goodService = new GoodServiceImpl();

        try {
            System.out.println("Name: ");
            String name = sc.nextLine();

            System.out.println("Delivery price: ");
            BigDecimal price = new BigDecimal(sc.nextLine());

            System.out.println("Category (FOOD, NON_FOOD): ");
            GoodsCategories category = GoodsCategories.valueOf(sc.nextLine().toUpperCase());

            System.out.println("Date (yyyy-MM-dd): ");
            LocalDate date = LocalDate.parse(sc.nextLine());

            System.out.println("Quantity: ");
            int quantity = Integer.parseInt(sc.nextLine());

            Goods good = new Goods(name, price, category, date, quantity);
            System.out.println(goodService.getSalePrice(good));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}