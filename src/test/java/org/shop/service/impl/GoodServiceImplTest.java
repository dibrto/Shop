package org.shop.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.shop.data.Good;
import org.shop.data.GoodCategories;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GoodServiceImplTest {

    private GoodServiceImpl service;
    private Good good;

    @BeforeEach
    void setUp() {
        service = new GoodServiceImpl();
        good = mock(Good.class);
        when(good.getDeliveryPrice()).thenReturn(new BigDecimal("10.00"));
    }

    private void withExpiry(LocalDate date) {
        when(good.getExpiryDate()).thenReturn(date);
    }

    private void withCategory(GoodCategories category) {
        when(good.getCategory()).thenReturn(category);
    }

    @Test
    void isExpired_shouldReturnFalse_whenExpiryIsInFuture() {
        withExpiry(LocalDate.now().plusDays(5));
        assertFalse(service.isExpired(good));
    }

    @Test
    void isExpired_shouldReturnFalse_whenExpiryIsToday() {
        withExpiry(LocalDate.now());
        assertFalse(service.isExpired(good));
    }

    @Test
    void isExpired_shouldReturnTrue_whenExpiryWasYesterday() {
        withExpiry(LocalDate.now().minusDays(1));
        assertTrue(service.isExpired(good));
    }

    @Test
    void getSalePrice_shouldThrowIllegalState_whenGoodIsExpired() {
        withExpiry(LocalDate.now().minusDays(1));
        withCategory(GoodCategories.FOOD);
        assertThrows(IllegalStateException.class, () -> service.getSalePrice(good));
    }

    @Test
    void getSalePrice_shouldNotApplyExpiryDiscountForFood_whenOutsideThreshold() {
        // FOOD threshold = 10 days
        withExpiry(LocalDate.now().plusDays(20));
        withCategory(GoodCategories.FOOD);

        assertEquals(new BigDecimal("13.00"), service.getSalePrice(good));
    }

    @Test
    void getSalePrice_shouldApplyExpiryDiscountForFood_whenWithinThreshold() {
        // FOOD threshold = 10 days
        withExpiry(LocalDate.now().plusDays(5));
        withCategory(GoodCategories.FOOD);

        assertEquals(new BigDecimal("11.70"), service.getSalePrice(good));
    }

    @Test
    void getSalePrice_shouldApplyExpiryDiscountForFood_whenThresholdInBoundary() {
        // FOOD threshold = 10 days
        withExpiry(LocalDate.now().plusDays(10));
        withCategory(GoodCategories.FOOD);

        assertEquals(new BigDecimal("11.70"), service.getSalePrice(good));
    }

    @Test
    void getSalePrice_shouldReturnMarkupOnlyForNonFood_whenOutsideThreshold() {
        // NON_FOOD threshold = 5 days
        withExpiry(LocalDate.now().plusDays(10));
        withCategory(GoodCategories.NON_FOOD);

        assertEquals(new BigDecimal("12.50"), service.getSalePrice(good));
    }

    @Test
    void getSalePrice_shouldApplyExpiryDiscountForNonFood_whenWithinThreshold() {
        // NON_FOOD threshold = 5 days
        withExpiry(LocalDate.now().plusDays(3));
        withCategory(GoodCategories.NON_FOOD);

        assertEquals(new BigDecimal("11.88"), service.getSalePrice(good));
    }

    @Test
    void getSalePrice_shouldApplyExpiryDiscountForNonFood_whenInThresholdBoundary() {
        // NON_FOOD threshold = 5 days
        withExpiry(LocalDate.now().plusDays(5));
        withCategory(GoodCategories.NON_FOOD);

        assertEquals(new BigDecimal("11.88"), service.getSalePrice(good));
    }
}
