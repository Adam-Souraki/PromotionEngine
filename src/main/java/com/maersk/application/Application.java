package com.maersk.application;

import com.maersk.contract.*;
import com.maersk.engine.PromotionService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Application {

    public static void main(String[] args) {

        Product p1 = new Product(1,"A", BigDecimal.valueOf(20));
        Product p2 = new Product(2,"B", BigDecimal.valueOf(10));
        Product p3 = new Product(3,"C", BigDecimal.valueOf(5));

        Order o1 = new Order(1);
        Item item = new Item(1, p1, 3D);
        Item item1 = new Item(2, p1, 2D);
        Item item2 = new Item(3, p2, 2D);
        o1.add(item);
        o1.add(item1);
        o1.add(item2);

        List<ProductQuantity> m = new ArrayList<>();
        m.add(new ProductQuantity(p1, 1D));
        m.add(new ProductQuantity(p2, 1D));
        //m.add(new ProductQuantity(p3, 1D));

        Promotion pr = new Promotion(1, m, BigDecimal.valueOf(17));

        PromotionService promotionService = new PromotionService();

        PriceInfo priceInfo = new PriceInfo();
        priceInfo.setOrder(o1);
        priceInfo.setPromotions(Arrays.asList(pr));

        promotionService.calcPrice(priceInfo);

    }

}
