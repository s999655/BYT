package test;

import main.product.Product;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class testProduct {

    private static final String TEST_FILE = "test_products.xml";

    @Test
    void constructor_setsFieldsCorrectly() {
        Product p = new Product(1, "Shoes", 120.0, 10, "ModelX", "Red", 100.0, "A1");

        assertEquals(1, p.getProductID());
        assertEquals("Shoes", p.getName());
        assertEquals(120.0, p.getPrice());
        assertEquals(10, p.getStockQuantity());
        assertEquals("ModelX", p.getModel());
        assertEquals("Red", p.getColor());
        assertEquals(100.0, p.getMinPrice());
        assertEquals("A1", p.getLocation());
    }

    @Test
    void setters_validateFields() {
        Product p = new Product();

        
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> p.setProductID(0));
        assertEquals("ProductID cannot be negative", e1.getMessage());

        p.setProductID(2);
        assertEquals(2, p.getProductID());

      
        Exception e2 = assertThrows(IllegalArgumentException.class, () -> p.setName(""));
        assertEquals("name cannot be null or empty", e2.getMessage());
        p.setName("T-Shirt");
        assertEquals("T-Shirt", p.getName());

      
        p.setMinPrice(50);
        Exception e3 = assertThrows(IllegalArgumentException.class, () -> p.setPrice(0));
        assertEquals("price cannot be zero", e3.getMessage());
        Exception e4 = assertThrows(IllegalArgumentException.class, () -> p.setPrice(40));
        assertEquals("price cannot be less than minimum price", e4.getMessage());
        p.setPrice(60);
        assertEquals(60, p.getPrice());

       
        Exception e5 = assertThrows(IllegalArgumentException.class, () -> p.setStockQuantity(-1));
        assertEquals("stockQuantity cannot be negative", e5.getMessage());
        p.setStockQuantity(5);
        assertEquals(5, p.getStockQuantity());

        
        Exception e6 = assertThrows(IllegalArgumentException.class, () -> p.setModel(null));
        assertEquals("model cannot be null or empty", e6.getMessage());
        p.setModel("M1");
        assertEquals("M1", p.getModel());

       
        Exception e7 = assertThrows(IllegalArgumentException.class, () -> p.setColor(""));
        assertEquals("color cannot be null or empty", e7.getMessage());
        p.setColor("Blue");
        assertEquals("Blue", p.getColor());

      
        Exception e8 = assertThrows(IllegalArgumentException.class, () -> p.setLocation(""));
        assertEquals("location cannot be null or empty", e8.getMessage());
        p.setLocation("B2");
        assertEquals("B2", p.getLocation());
    }

    @Test
    void extent_addsProductsAndIsUnmodifiable() {
        Product.loadExtent(TEST_FILE);  

        Product p1 = new Product(1, "Shoes", 120.0, 10, "ModelX", "Red", 100.0, "A1");
        Product p2 = new Product(2, "Shirt", 50.0, 20, "ModelY", "Blue", 40.0, "B1");

        List<Product> extent = Product.getExtent();
        assertTrue(extent.contains(p1));
        assertTrue(extent.contains(p2));
        assertThrows(UnsupportedOperationException.class, () -> extent.clear());
    }

    @Test
    void extentPersistence_saveAndLoadWorks() {
        Product.loadExtent(TEST_FILE);  

        Product p1 = new Product(1, "Shoes", 120.0, 10, "ModelX", "Red", 100.0, "A1");
        Product p2 = new Product(2, "Shirt", 50.0, 20, "ModelY", "Blue", 40.0, "B1");

        Product.saveExtent(TEST_FILE);
        Product.loadExtent(TEST_FILE);

        List<Product> loadedExtent = Product.getExtent();
        assertEquals(2, loadedExtent.size());
        assertEquals(1, loadedExtent.get(0).getProductID());
        assertEquals(2, loadedExtent.get(1).getProductID());
    }
}
