package com.maersk.contract;

import java.util.ArrayList;
import java.util.List;

public class PriceInfo {

    private Order order;
    private List<Promotion> promotions = new ArrayList<>();

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

}
