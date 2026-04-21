package org.shop.data;

import java.math.BigDecimal;

public enum GoodsCategories {
    FOOD(new BigDecimal("30"), 10, new BigDecimal("20")),
    NON_FOOD(new BigDecimal("25"), 5, new BigDecimal("5"));

    private BigDecimal markupRate;
    private int expirationThresholdDays;
    private BigDecimal expiryDiscountRate;

    GoodsCategories(BigDecimal markupRate, int expirationThresholdDays, BigDecimal expiryDiscountRate) {
        setMarkupRate(markupRate);
        setExpirationThresholdDays(expirationThresholdDays);
        setExpiryDiscountRate(expiryDiscountRate);
    }

    public BigDecimal getMarkupRate() {
        return markupRate;
    }

    public void setMarkupRate(BigDecimal markupRate) {
        if  (markupRate.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Markup Rate must be greater than zero");
        }

        this.markupRate = markupRate;
    }

    public int getExpirationThresholdDays() {
        return expirationThresholdDays;
    }

    public void setExpirationThresholdDays(int expirationThresholdDays) {
        if (expirationThresholdDays <= 0){
            throw new IllegalArgumentException("Expiration Threshold Days must be greater than zero");
        }

        this.expirationThresholdDays = expirationThresholdDays;
    }

    public BigDecimal getExpiryDiscountRate() {
        return expiryDiscountRate;
    }

    public void setExpiryDiscountRate(BigDecimal expiryDiscountRate) {
        if (expiryDiscountRate.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Expiry Discount Rate must be greater than zero");
        }

        this.expiryDiscountRate = expiryDiscountRate;
    }
}
