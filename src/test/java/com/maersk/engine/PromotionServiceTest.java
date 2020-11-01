package com.maersk.engine;

import com.maersk.contract.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;


import static org.junit.jupiter.api.Assertions.*;

class PromotionServiceTest {

    private PromotionService promotionService;

    private Product productA;
    private Product productB;
    private Product productC;
    private Product productD;

    private Promotion promotion1;
    private Promotion promotion2;
    private Promotion promotion3;

    private Order order;

    PromotionServiceTest() {
        promotionService = new PromotionService();

        productA = new Product(1, "A", BigDecimal.valueOf(50));
        productB = new Product(2, "B", BigDecimal.valueOf(30));
        productC = new Product(3, "C", BigDecimal.valueOf(20));
        productD = new Product(4, "D", BigDecimal.valueOf(15));

        promotion1 = new Promotion(
                1, Arrays.asList(new ProductQuantity(productA, 3D)), BigDecimal.valueOf(130));
        promotion2 = new Promotion(
                2, Arrays.asList(new ProductQuantity(productB, 2D)), BigDecimal.valueOf(45));
        promotion3 = new Promotion(
                3, Arrays.asList(new ProductQuantity(productC, 1D), new ProductQuantity(productD, 1D)),
                BigDecimal.valueOf(30));

        order = new Order(1);
    }

    @Test
    void calcPrice_Scenario_A() {

        order.add(new Item(1, productA, 1D));
        order.add(new Item(2, productB, 1D));
        order.add(new Item(3, productC, 1D));

        PriceInfo priceInfo = new PriceInfo();
        priceInfo.setOrder(order);
        priceInfo.setPromotions(Arrays.asList(promotion1, promotion2, promotion3));

        assertEquals(BigDecimal.valueOf(100.0), promotionService.calcPrice(priceInfo));
    }

    @Test
    void calcPrice_Scenario_B() {

        order.add(new Item(1, productA, 5D));
        order.add(new Item(2, productB, 5D));
        order.add(new Item(3, productC, 1D));

        PriceInfo priceInfo = new PriceInfo();
        priceInfo.setOrder(order);
        priceInfo.setPromotions(Arrays.asList(promotion1, promotion2, promotion3));

        assertEquals(BigDecimal.valueOf(370.0), promotionService.calcPrice(priceInfo));
    }

    @Test
    void calcPrice_Scenario_C() {

        order.add(new Item(1, productA, 3D));
        order.add(new Item(2, productB, 5D));
        order.add(new Item(3, productC, 1D));
        order.add(new Item(4, productD, 1D));

        PriceInfo priceInfo = new PriceInfo();
        priceInfo.setOrder(order);
        priceInfo.setPromotions(Arrays.asList(promotion1, promotion2, promotion3));

        assertEquals(BigDecimal.valueOf(280.0), promotionService.calcPrice(priceInfo));
    }

    @Test
    void calcPrice_Scenario_Other() {

        Promotion pro1 = new Promotion(
                1,
                Arrays.asList(
                        new ProductQuantity(productA, 3D),
                        new ProductQuantity(productB, 2D)),
                BigDecimal.valueOf(130));
        Promotion pro2 = new Promotion(
                2,
                Arrays.asList(
                        new ProductQuantity(productB, 1D),
                        new ProductQuantity(productC, 1D),
                        new ProductQuantity(productD, 1D)),
                BigDecimal.valueOf(45));
        Promotion pro3 = new Promotion(
                3,
                Arrays.asList(new ProductQuantity(productD, 4D)),
                BigDecimal.valueOf(30));

        order.add(new Item(1, productA, 8D));
        order.add(new Item(2, productB, 7D));
        order.add(new Item(3, productC, 1D));
        order.add(new Item(4, productD, 1D));

        PriceInfo priceInfo = new PriceInfo();
        priceInfo.setOrder(order);
        priceInfo.setPromotions(Arrays.asList(pro1, pro2, pro3));

        assertEquals(BigDecimal.valueOf(465.0), promotionService.calcPrice(priceInfo));
    }

}