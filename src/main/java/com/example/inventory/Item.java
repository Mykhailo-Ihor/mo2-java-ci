package com.example.inventory;

import java.util.Objects;

public record Item(String sku, int quantity) {

    public Item {
        Objects.requireNonNull(sku, "sku");

        if (sku.isBlank()) {
            throw new IllegalArgumentException("sku must not be blank");
        }

        if (quantity < 0) {
            throw new IllegalArgumentException("quantity must not be negative");
        }
    }
}
