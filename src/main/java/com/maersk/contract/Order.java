package com.maersk.contract;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private Integer id;
    private List<Item> items = new ArrayList<>();

    public Order (Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void add (Item item) {
        this.items.add(item);
    }

    public void addAll (List<Item> items) {
        this.items.addAll(items);
    }

    public void remove (Item item) {
        this.items.remove(item);
    }

    public void  clear () {
        this.items.clear();
    }

}
