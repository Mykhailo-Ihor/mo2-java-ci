package com.example.inventory;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class Inventory {

    private final Map<String, Integer> stock = new LinkedHashMap<>();

    public void receive(Item item) {
        stock.merge(item.sku(), item.quantity(), Integer::sum);
    }

    public boolean dispatch(String sku, int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("quantity must be positive");
        }

        int available = stock.getOrDefault(sku, 0);

        if (available < quantity) {
            return false;
        }

        stock.put(sku, available - quantity);
        return true;
    }

    public Optional<Integer> quantityOf(String sku) {
        return Optional.ofNullable(stock.get(sku));
    }

    public int totalUnits() {
        return stock.values().stream().mapToInt(Integer::intValue).sum();
    }

    public Map<String, Integer> snapshot() {
        return Collections.unmodifiableMap(new LinkedHashMap<>(stock));
    }
}
