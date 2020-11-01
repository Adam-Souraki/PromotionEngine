package com.maersk.contract;

import java.math.BigDecimal;
import java.util.List;

public class Promotion {

    private Integer id;
    private List<ProductQuantity> productQuantities;
    private BigDecimal price;

    public Promotion (Integer id, List<ProductQuantity> productQuantities, BigDecimal price) {
        this.id = id;
        this.productQuantities = productQuantities;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ProductQuantity> getProductQuantities() {
        return productQuantities;
    }

    public void setProductQuantities(List<ProductQuantity> productQuantities) {
        this.productQuantities = productQuantities;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
