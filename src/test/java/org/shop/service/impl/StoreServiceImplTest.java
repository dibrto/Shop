package org.shop.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.shop.data.Cashier;
import org.shop.data.Good;
import org.shop.data.Receipt;
import org.shop.data.Store;
import org.shop.exception.GoodNotFoundException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StoreServiceImplTest {

    private StoreServiceImpl service;
    private Store store;
    private Good good;
    private List<Good> deliveredGoods;
    private Map<Good, Integer> soldGoods;
    private List<Receipt> receipts;
    private List<Cashier> cashiers;

    @BeforeEach
    void setUp() {
        service = spy(new StoreServiceImpl());
        store = mock(Store.class);
        good = mock(Good.class);

        deliveredGoods = new ArrayList<>();
        soldGoods = new HashMap<>();
        receipts = new ArrayList<>();
        cashiers = new ArrayList<>();

        when(store.getDeliveredGoods()).thenReturn(deliveredGoods);
        when(store.getSoldGoods()).thenReturn(soldGoods);
        when(store.getReceipts()).thenReturn(receipts);
        when(store.getCashiers()).thenReturn(cashiers);
    }

    // --- deliveryGood ---

    @Test
    void deliveryGood_shouldAddGoodToDeliveredGoods() {
        service.deliveryGood(store, good);
        assertTrue(deliveredGoods.contains(good));
    }

    @Test
    void deliveryGood_shouldInitializeSoldQuantityToZero() {
        service.deliveryGood(store, good);
        assertEquals(0, soldGoods.get(good));
    }

    // --- soldGoods ---

    @Test
    void soldGoods_shouldReduceGoodQuantity() {
        when(good.getQuantity()).thenReturn(10);
        service.soldGoods(store, Map.of(good, 3));
        assertEquals(3, soldGoods.get(good));
    }

    @Test
    void soldGoods_shouldUpdateSoldGoodsMap() {
        when(good.getQuantity()).thenReturn(10);
        service.soldGoods(store, Map.of(good, 3));
        assertEquals(3, soldGoods.get(good));
    }

    @Test
    void soldGoods_shouldAccumulateSoldQuantity_whenGoodAlreadyInSoldGoods() {
        soldGoods.put(good, 2);
        when(good.getQuantity()).thenReturn(10);
        service.soldGoods(store, Map.of(good, 3));
        assertEquals(5, soldGoods.get(good));
    }

    // --- findGoodById ---

    @Test
    void findGoodById_shouldReturnGood_whenIdExists() throws GoodNotFoundException {
        when(good.getId()).thenReturn(1);
        deliveredGoods.add(good);
        assertSame(good, service.findGoodById(store, 1));
    }

    @Test
    void findGoodById_shouldThrowGoodNotFoundException_whenIdNotFound() {
        when(good.getId()).thenReturn(1);
        deliveredGoods.add(good);
        assertThrows(GoodNotFoundException.class, () -> service.findGoodById(store, 99));
    }

    // --- addReceipt ---

    @Test
    void addReceipt_shouldAddReceiptToStore() {
        Receipt receipt = mock(Receipt.class);
        service.addReceipt(store, receipt);
        assertTrue(receipts.contains(receipt));
    }

    // --- addCashier ---

    @Test
    void addCashier_shouldAddCashierToStore() {
        Cashier cashier = mock(Cashier.class);
        service.addCashier(store, cashier);
        assertTrue(cashiers.contains(cashier));
    }

    // --- getRevenue ---

    @Test
    void getRevenue_shouldReturnZero_whenNoReceipts() {
        assertEquals(BigDecimal.ZERO, service.getRevenue(store));
    }

    @Test
    void getRevenue_shouldReturnSumOfAllReceiptTotals_whenReceiptsExist() {
        Receipt receipt1 = mock(Receipt.class);
        Receipt receipt2 = mock(Receipt.class);
        when(receipt1.getTotalSum()).thenReturn(new BigDecimal("100.00"));
        when(receipt2.getTotalSum()).thenReturn(new BigDecimal("200.00"));

        receipts.add(receipt1);
        receipts.add(receipt2);

        assertEquals(new BigDecimal("300.00"), service.getRevenue(store));
    }

    // --- getExpenses ---

    @Test
    void getExpenses_shouldReturnSalariesPlusDeliveryCosts() {
        Cashier cashier = mock(Cashier.class);
        when(cashier.getSalary()).thenReturn(new BigDecimal("1000.00"));
        cashiers.add(cashier);

        soldGoods.put(good, 3);
        when(good.getQuantity()).thenReturn(7);
        when(good.getDeliveryPrice()).thenReturn(new BigDecimal("5.00"));

        assertEquals(new BigDecimal("1050.00"), service.getExpenses(store));
    }

    // --- getProfit ---

    @Test
    void getProfit_shouldReturnRevenueMinusExpenses_whenCalled() {
        doReturn(new BigDecimal("500.00")).when(service).getRevenue(store);
        doReturn(new BigDecimal("300.00")).when(service).getExpenses(store);
        assertEquals(new BigDecimal("200.00"), service.getProfit(store));
    }
}
