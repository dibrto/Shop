package org.shop.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.shop.data.CashDesk;
import org.shop.data.Good;
import org.shop.exception.InsufficientQuantityException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CashDeskServiceImplTest {

    private GoodServiceImpl goodService;
    private CashDeskServiceImpl service;
    private CashDesk cashDesk;
    private Good good;
    private Map<Good, Integer> cart;

    @BeforeEach
    void setUp() {
        goodService = mock(GoodServiceImpl.class);
        service = new CashDeskServiceImpl(goodService);
        cashDesk = mock(CashDesk.class);
        good = mock(Good.class);
        cart = new HashMap<>();
        when(cashDesk.getCurrCart()).thenReturn(cart);
    }

    // --- addToCart ---

    @Test
    void addToCart_shouldThrowInsufficientQuantity_whenRequestedQuantityExceedsStock() {
        when(good.getQuantity()).thenReturn(2);
        assertThrows(InsufficientQuantityException.class, () -> service.addToCart(cashDesk, good, 5));
    }

    @Test
    void addToCart_shouldThrowIllegalState_whenGoodIsExpired() {
        when(good.getQuantity()).thenReturn(10);
        when(goodService.isExpired(good)).thenReturn(true);
        assertThrows(IllegalStateException.class, () -> service.addToCart(cashDesk, good, 1));
    }

    @Test
    void addToCart_shouldAddGoodToCart_whenValidAndNotExpired() throws InsufficientQuantityException {
        when(good.getQuantity()).thenReturn(10);
        when(goodService.isExpired(good)).thenReturn(false);

        service.addToCart(cashDesk, good, 3);

        assertEquals(3, cart.get(good));
    }

    @Test
    void addToCart_shouldAccumulateQuantity_whenGoodAlreadyInCart() throws InsufficientQuantityException {
        cart.put(good, 2);
        when(good.getQuantity()).thenReturn(10);
        when(goodService.isExpired(good)).thenReturn(false);

        service.addToCart(cashDesk, good, 3);

        assertEquals(5, cart.get(good));
    }

    // --- emptyCart ---

    @Test
    void emptyCart_shouldClearAllItemsFromCart() {
        cart.put(good, 3);

        service.emptyCart(cashDesk);

        assertTrue(cart.isEmpty());
    }

    // --- getCartPrice ---

    @Test
    void getCartPrice_shouldReturnZero_whenCartIsEmpty() {
        assertEquals(BigDecimal.ZERO, service.getCartPrice(cashDesk));
    }

    @Test
    void getCartPrice_shouldReturnSalePriceMultipliedByQuantity_whenSingleItem() {
        cart.put(good, 3);
        when(goodService.getSalePrice(good)).thenReturn(new BigDecimal("10.00"));

        assertEquals(new BigDecimal("30.00"), service.getCartPrice(cashDesk));
    }

    @Test
    void getCartPrice_shouldReturnSumOfAllItems_whenMultipleItems() {
        cart.put(good, 2);
        Good good2 = mock(Good.class);
        cart.put(good2, 4);

        when(goodService.getSalePrice(good)).thenReturn(new BigDecimal("5.00"));
        when(goodService.getSalePrice(good2)).thenReturn(new BigDecimal("3.00"));

        assertEquals(new BigDecimal("22.00"), service.getCartPrice(cashDesk));
    }
}
