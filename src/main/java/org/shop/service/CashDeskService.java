package org.shop.service;

import org.shop.data.CashDesk;
import org.shop.exception.GoodNotFoundException;
import org.shop.exception.InsufficientQuantityException;

public interface CashDeskService {
    void scanGood(CashDesk cashDesk, int id, int quantity) throws GoodNotFoundException, InsufficientQuantityException;
}
