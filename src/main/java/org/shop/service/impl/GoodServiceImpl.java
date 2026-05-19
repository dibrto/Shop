package org.shop.service.impl;

import org.shop.data.Good;
import org.shop.service.GoodService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class GoodServiceImpl implements GoodService {

    private int getDaysUntilExpiry(Good good){
        return (int) ChronoUnit.DAYS.between(LocalDate.now(),good.getExpiryDate());
    }

    private boolean isExpired(Good good){
        return getDaysUntilExpiry(good) < 0;
    }

    private boolean isInThresholdDays(Good good) {
        return good.getCategory().getExpirationThresholdDays() >= getDaysUntilExpiry(good);
    }

    @Override
    public BigDecimal getSalePrice(Good good) {
        if (isExpired(good)) {
            throw new IllegalStateException("Expired goods cannot be sold");
        }

        BigDecimal salePrice = good.getDeliveryPrice().multiply(good.getCategory().getMarkupRate());

        if (isInThresholdDays(good)) {
            System.out.println(good.getCategory().getExpiryDiscountRate());
            salePrice = salePrice.multiply(good.getCategory().getExpiryDiscountRate());
        }

        return salePrice.setScale(2, RoundingMode.HALF_UP);
    }
}
