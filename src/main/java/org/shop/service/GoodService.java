package org.shop.service;

import org.shop.data.Goods;

import java.math.BigDecimal;

public interface GoodService {
    BigDecimal getSalePrice(Goods good);
}
