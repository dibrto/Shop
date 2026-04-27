package org.shop.service;

import org.shop.data.Good;

import java.math.BigDecimal;

public interface GoodService {
    BigDecimal getSalePrice(Good good);
}
