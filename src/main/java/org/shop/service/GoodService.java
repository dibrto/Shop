package org.shop.service;

import org.shop.data.Good;

import java.math.BigDecimal;

public interface GoodService {
    boolean isExpired(Good good);
    BigDecimal getSalePrice(Good good);
}
