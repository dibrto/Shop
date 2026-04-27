package org.shop.service.impl;

import org.shop.data.Goods;
import org.shop.service.GoodService;

import java.math.BigDecimal;

public class GoodServiceImpl implements GoodService {

    @Override
    public BigDecimal getSalePrice(Goods good) {
        return good.getDeliveryPrice().multiply(good.getCategory().getMarkupRate());
    }
}
