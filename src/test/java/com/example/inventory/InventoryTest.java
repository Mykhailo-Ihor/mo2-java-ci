package com.example.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InventoryTest {

    private Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
    }

    @Test
    @DisplayName("receiving stock accumulates quantities per sku")
    void receiveAccumulates() {
        inventory.receive(new Item("SKU-1", 3));
        inventory.receive(new Item("SKU-1", 4));

        assertEquals(Optional.of(7), inventory.quantityOf("SKU-1"));
    }

    @Test
    @DisplayName("dispatch reduces available stock")
    void dispatchReducesStock() {
        inventory.receive(new Item("SKU-1", 10));

        assertTrue(inventory.dispatch("SKU-1", 4));
        assertEquals(Optional.of(6), inventory.quantityOf("SKU-1"));
    }

    @Test
    @DisplayName("dispatch fails when stock is insufficient")
    void dispatchFailsWhenShort() {
        inventory.receive(new Item("SKU-1", 2));

        assertFalse(inventory.dispatch("SKU-1", 3));
        assertEquals(Optional.of(2), inventory.quantityOf("SKU-1"));
    }

    @Test
    @DisplayName("dispatch fails for an unknown sku")
    void dispatchFailsForUnknownSku() {
        assertFalse(inventory.dispatch("MISSING", 1));
    }

    @Test
    @DisplayName("dispatch rejects a non-positive quantity")
    void dispatchRejectsNonPositive() {
        assertThrows(IllegalArgumentException.class, () -> inventory.dispatch("SKU-1", 0));
    }

    @Test
    @DisplayName("unknown skus have no quantity")
    void unknownSkuHasNoQuantity() {
        assertEquals(Optional.empty(), inventory.quantityOf("MISSING"));
    }

    @Test
    @DisplayName("total units sums every sku")
    void totalUnitsSumsEverything() {
        inventory.receive(new Item("SKU-1", 3));
        inventory.receive(new Item("SKU-2", 9));

        assertEquals(12, inventory.totalUnits());
    }

    @Test
    @DisplayName("sku count tracks distinct skus")
    void skuCountTracksDistinctSkus() {
        inventory.receive(new Item("SKU-1", 1));
        inventory.receive(new Item("SKU-1", 2));
        inventory.receive(new Item("SKU-2", 1));

        assertEquals(2, inventory.skuCount());
    }

    @Test
    @DisplayName("in stock is true only while units remain")
    void inStockReflectsRemainingUnits() {
        inventory.receive(new Item("SKU-1", 1));
        assertTrue(inventory.isInStock("SKU-1"));

        inventory.dispatch("SKU-1", 1);
        assertFalse(inventory.isInStock("SKU-1"));
    }

    @Test
    @DisplayName("unknown skus are not in stock")
    void unknownSkuIsNotInStock() {
        assertFalse(inventory.isInStock("MISSING"));
    }

    @Test
    @DisplayName("snapshot is not writable by callers")
    void snapshotIsUnmodifiable() {
        inventory.receive(new Item("SKU-1", 1));

        assertThrows(
                UnsupportedOperationException.class,
                () -> inventory.snapshot().put("SKU-2", 5));
    }
}
