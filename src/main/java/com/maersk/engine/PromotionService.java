package com.maersk.engine;

import com.maersk.contract.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


public class PromotionService {

    /**
     * Return total price of order list.
     * First find match promotion in order list and calculate price then compute rest of order.
     *
     * @param priceInfo Included order and promotion lists
     * @return Total price of order list
     */
    public BigDecimal calcPrice(PriceInfo priceInfo) {

        List<BigDecimal> prices = new ArrayList<>();
        double promotionTotalPrice = 0;
        double nonPromotionTotalPrice = 0;

        // Sum of quantities group by order product
        Map<Product, Double> map = priceInfo.getOrder().getItems().stream().collect(
                Collectors.groupingBy(Item::getProduct, Collectors.summingDouble(Item::getQuantity))
        );

        // Convert map to list
        List<ProductQuantity> list = map.entrySet().stream().map(
                x -> new ProductQuantity(x.getKey(), x.getValue())).collect(Collectors.toList()
        );

        for (Promotion promotion : priceInfo.getPromotions()) {
            // Find match order with promotion
            while (isPromotionMatch(list, promotion)) {
                prices.add(promotion.getPrice());
                for (ProductQuantity pq : promotion.getProductQuantities()) {
                    ProductQuantity t = list.stream().filter(x -> x.getProduct() == pq.getProduct()).findFirst().orElse(null);
                    t.setQuantity(t.getQuantity() - pq.getQuantity());
                }
            }

            promotionTotalPrice = prices.stream().mapToDouble(BigDecimal::doubleValue).sum();
            nonPromotionTotalPrice = list.stream().mapToDouble(x -> x.getQuantity() * x.getProduct().getPrice().doubleValue()).sum();
        }

        return BigDecimal.valueOf(promotionTotalPrice + nonPromotionTotalPrice);
    }

    /**
     * Return true if the list contains promotion
     *
     * @param productQuantities list is must search into
     * @param promotion element to search for
     * @return true if the list contains promotion
     */
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
