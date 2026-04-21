package org.shop;

import org.shop.data.Goods;
import org.shop.data.GoodsCategories;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Delivery price: ");
            BigDecimal price = new BigDecimal(sc.nextLine());

            System.out.print("Category (FOOD, NON_FOOD): ");
            GoodsCategories category = GoodsCategories.valueOf(sc.nextLine().toUpperCase());

            System.out.print("Date (yyyy-MM-dd): ");
            LocalDate date = LocalDate.parse(sc.nextLine());

            System.out.print("Quantity: ");
            int quantity = Integer.parseInt(sc.nextLine());

            Goods good = new Goods(name, price, category, date, quantity);
            System.out.println(good);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}