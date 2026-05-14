package org.shop.exception;

import org.shop.data.Good;

public class InsufficientQuantityException extends Exception {
    public InsufficientQuantityException(Good good, int requestedQuantity) {
        super(good.getName() + " has insufficient quantity. \n"
                + "Requested: " + requestedQuantity
                + ", Available: " + good.getQuantity()
                + ", Missing: " + (requestedQuantity - good.getQuantity()));
    }
}
