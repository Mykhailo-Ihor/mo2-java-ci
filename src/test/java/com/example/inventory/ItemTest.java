package com.example.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ItemTest {

    @Test
    @DisplayName("accepts a valid sku and quantity")
    void acceptsValidItem() {
        Item item = new Item("SKU-1", 5);

        assertEquals("SKU-1", item.sku());
        assertEquals(5, item.quantity());
    }

    @Test
    @DisplayName("rejects a blank sku")
    void rejectsBlankSku() {
        assertThrows(IllegalArgumentException.class, () -> new Item("  ", 1));
    }

    @Test
    @DisplayName("rejects a null sku")
    void rejectsNullSku() {
        assertThrows(NullPointerException.class, () -> new Item(null, 1));
    }

    @Test
    @DisplayName("rejects a negative quantity")
    void rejectsNegativeQuantity() {
        assertThrows(IllegalArgumentException.class, () -> new Item("SKU-1", -1));
    }
}
