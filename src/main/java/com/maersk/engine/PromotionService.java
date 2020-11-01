package com.maersk.engine;

import com.maersk.contract.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class PromotionService {

    public BigDecimal calcPrice(PriceInfo priceInfo) {

        List<BigDecimal> prices = new ArrayList<>();

        // Sum of quantities group by order product
        Map<Product, Double> map = priceInfo.getOrder().getItems().stream().collect(
                Collectors.groupingBy(Item::getProduct, Collectors.summingDouble(Item::getQuantity))
        );

        List<ProductQuantity> list = map.entrySet().stream().map(
                x -> new ProductQuantity(x.getKey(), x.getValue())).collect(Collectors.toList()
        );

        for (Promotion promotion : priceInfo.getPromotions()) {

            // Find match order with promotion
            boolean match = true;
            List<ProductQuantity> temp = new ArrayList<>();

            // for (ProductQuantity productQuantity : promotion.getProductQuantities()) {
            //     ProductQuantity pq = list.stream().filter(
            //             x -> x.getProduct() == productQuantity.getProduct() &&
            //                     x.getQuantity() >= productQuantity.getQuantity()
            //     ).findFirst().orElse(null);
            //
            //     match = match && pq != null;
            //     if (pq != null){
            //         temp.add(pq);
            //     }
            // }

            while (isPromotionMatch(list, promotion)) {
                prices.add(promotion.getPrice());
                for (ProductQuantity pq : promotion.getProductQuantities()) {
                    ProductQuantity t = list.stream().filter(x -> x.getProduct() == pq.getProduct()).findFirst().orElse(null);
                    t.setQuantity(t.getQuantity() - pq.getQuantity());
                }

            }


            System.out.println(prices.stream().mapToDouble(BigDecimal::doubleValue).sum());

        }

        return BigDecimal.valueOf(0);

    }

    private boolean isPromotionMatch(List<ProductQuantity> productQuantities, Promotion promotion) {
        boolean result = true;

        for (ProductQuantity promotionPQ : promotion.getProductQuantities()) {
            result = result && productQuantities.stream().anyMatch(
                    x -> x.getProduct() == promotionPQ.getProduct() &&
                            x.getQuantity() >= promotionPQ.getQuantity()
            );
        }

        return result;
    }

}
