package test;

import main.product.Brand;
import main.product.Product;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

class TestProductReflex {

    static class TestProduct extends Product {

        public TestProduct(int id, Brand brand, String name) {
            super(id, brand, name, 100.0, 10, "model", "color", 50.0, "A1");
        }

        @Override
        void removeFromExtent() {
            // no extent tracking needed for this test
        }
    }

    @Test
    void addContainedProduct_updatesBothSides() {
        Brand brand = new Brand("Test", LocalDate.of(2000, 1, 1));
        Product kit = new TestProduct(1, brand, "Kit");
        Product item = new TestProduct(2, brand, "Item");

        kit.addContainedProduct(item);

        assertTrue(kit.getContains().contains(item));
        assertTrue(item.getContainedIn().contains(kit));
    }

    @Test
    void removeContainedProduct_updatesBothSides() {
        Brand brand = new Brand("Test", LocalDate.of(2000, 1, 1));
        Product kit = new TestProduct(3, brand, "Kit");
        Product item = new TestProduct(4, brand, "Item");

        kit.addContainedProduct(item);
        kit.removeContainedProduct(item);

        assertFalse(kit.getContains().contains(item));
        assertFalse(item.getContainedIn().contains(kit));
    }

    @Test
    void addContainedProduct_sameItemTwice_doesNotDuplicate() {
        Brand brand = new Brand("Test", LocalDate.of(2000, 1, 1));
        Product kit = new TestProduct(5, brand, "Kit");
        Product item = new TestProduct(6, brand, "Item");

        kit.addContainedProduct(item);
        kit.addContainedProduct(item);

        assertEquals(1, kit.getContains().size());
        assertEquals(1, item.getContainedIn().size());
    }

    @Test
    void addContainedProduct_self_throwsException() {
        Brand brand = new Brand("Test", LocalDate.of(2000, 1, 1));
        Product kit = new TestProduct(7, brand, "SelfKit");

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> kit.addContainedProduct(kit)
        );

        assertEquals("A product cannot contain itself", ex.getMessage());
    }

    @Test
    void gettersReturnUnmodifiableLists_encapsulation() {
        Brand brand = new Brand("Test", LocalDate.of(2000, 1, 1));
        Product kit = new TestProduct(8, brand, "Kit");
        Product item = new TestProduct(9, brand, "Item");

        kit.addContainedProduct(item);

        List<Product> contains = kit.getContains();
        List<Product> containedIn = item.getContainedIn();

        assertThrows(UnsupportedOperationException.class,
                () -> contains.add(new TestProduct(10, brand, "X")));
