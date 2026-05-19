package org.shop.service;

import org.shop.data.CashDesk;
import org.shop.data.Good;
import org.shop.exception.InsufficientQuantityException;

public interface CashDeskService {
    void scanGood(CashDesk cashDesk, Good good, int quantity) throws InsufficientQuantityException;
    void emptyCart(CashDesk cashDesk);
}
