package test;

import main.product.Product;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;

class TestProductReflex{

    static class TestProduct extends Product {
        public TestProduct(int id, String name) {
            super(id, name, 100.0, 10, "model", "color", 50.0, "A1");
        }
    }

    @Test
    void addContainedProduct_updatesBothSides() {
        Product kit = new TestProduct(1, "Kit");
        Product item = new TestProduct(2, "Item");

        kit.addContainedProduct(item);

        assertTrue(kit.getContains().contains(item));
        assertTrue(item.getContainedIn().contains(kit));
    }

    @Test
    void removeContainedProduct_updatesBothSides() {
        Product kit = new TestProduct(3, "Kit");
        Product item = new TestProduct(4, "Item");

        kit.addContainedProduct(item);
        kit.removeContainedProduct(item);

        assertFalse(kit.getContains().contains(item));
        assertFalse(item.getContainedIn().contains(kit));
    }

    @Test
    void addContainedProduct_sameItemTwice_doesNotDuplicate() {
        Product kit = new TestProduct(5, "Kit");
        Product item = new TestProduct(6, "Item");

        kit.addContainedProduct(item);
        kit.addContainedProduct(item);

        assertEquals(1, kit.getContains().size());
        assertEquals(1, item.getContainedIn().size());
    }

    @Test
    void addContainedProduct_self_throwsException() {
        Product kit = new TestProduct(7, "SelfKit");

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> kit.addContainedProduct(kit)
        );

        assertEquals("A product cannot contain itself", ex.getMessage());
    }

    @Test
    void gettersReturnUnmodifiableLists_encapsulation() {
        Product kit = new TestProduct(8, "Kit");
        Product item = new TestProduct(9, "Item");

        kit.addContainedProduct(item);

        List<Product> contains = kit.getContains();
        List<Product> containedIn = item.getContainedIn();

        assertThrows(UnsupportedOperationException.class, () -> contains.add(new TestProduct(10, "X")));
        assertThrows(UnsupportedOperationException.class, () -> containedIn.clear());
    }
}
