package org.shop.exception;

public class GoodNotFoundException extends Exception {
    public GoodNotFoundException(int id) {
        super("Good with id " + id + " not found");
    }
}
