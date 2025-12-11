package test;

import main.purchase.*;
import main.product.Clothing;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestPurchase {

    private static final String TEST_FILE = "test_purchases.xml";

    @BeforeEach
    @AfterEach 
    void cleanup() {
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void constructorWithoutDiscount_createsPurchaseCorrectly() {
        Clothing product1 = new Clothing(1, "Sweater", 100.0, 10, "ModelX", "Red", 100.0, "A1", new String[]{"Cashmere"}, Clothing.Size.M, Clothing.Category.women);
        Purchase p = new Purchase(1, Purchase.PaymentMethod.CASH, LocalDate.now(), java.util.Arrays.asList(product1));

        assertEquals(1, p.getTransactionID());
        assertEquals(Purchase.PaymentMethod.CASH, p.getPaymentMethod());
        assertEquals(100.0, p.getFinalPrice());
        assertTrue(Purchase.getExtent().contains(p));
    }

    @Test
    void constructorWithDiscount_createsPurchaseCorrectly() {
        Clothing product1 = new Clothing(1, "Sweater", 100.0, 10, "ModelX", "Red", 100.0, "A1", new String[]{"Cashmere"}, Clothing.Size.M, Clothing.Category.women);
        Promotion promo = new Promotion("Winter Sale", LocalDate.now(), LocalDate.now().plusDays(10), 0.1f); // 10% discount

        Purchase p = new Purchase(2, Purchase.PaymentMethod.CARD, LocalDate.now(), java.util.Arrays.asList(product1), promo);

        assertEquals(90.0, p.getFinalPrice(), 0.001); 
        assertTrue(Purchase.getExtent().contains(p));
    }

    @Test
    void setTransactionID_invalid_throwsException() {
        Clothing product1 = new Clothing(1, "Sweater", 100.0, 10, "ModelX", "Red", 100.0, "A1", new String[]{"Cashmere"}, Clothing.Size.M, Clothing.Category.women);
        Purchase p = new Purchase(1, Purchase.PaymentMethod.CASH, LocalDate.now(), java.util.Arrays.asList(product1));

        Exception e = assertThrows(IllegalArgumentException.class, () -> p.setTransactionID(0));
        assertEquals("transactionID must be positive", e.getMessage());
    }

    @Test
    void setPaymentMethod_null_throwsException() {
        Clothing product1 = new Clothing(1, "Sweater", 100.0, 10, "ModelX", "Red", 100.0, "A1", new String[]{"Cashmere"}, Clothing.Size.M, Clothing.Category.women);
        Purchase p = new Purchase(1, Purchase.PaymentMethod.CASH, LocalDate.now(), java.util.Arrays.asList(product1));

        Exception e = assertThrows(IllegalArgumentException.class, () -> p.setPaymentMethod(null));
        assertEquals("paymentMethod cannot be null", e.getMessage());
    }

    @Test
    void setPurchaseDate_nullOrFuture_throwsException() {
        Clothing product1 = new Clothing(1, "Sweater", 100.0, 10, "ModelX", "Red", 100.0, "A1", new String[]{"Cashmere"}, Clothing.Size.M, Clothing.Category.women);
        Purchase p = new Purchase(1, Purchase.PaymentMethod.CASH, LocalDate.now(), java.util.Arrays.asList(product1));

        assertThrows(IllegalArgumentException.class, () -> p.setPurchaseDate(null));
        assertThrows(IllegalArgumentException.class, () -> p.setPurchaseDate(LocalDate.now().plusDays(1)));
    }

    @Test
    void extentPersistence_saveAndLoadRestoresPurchases() {
        Clothing product1 = new Clothing(1, "Sweater", 100.0, 10, "ModelX", "Red", 100.0, "A1", new String[]{"Cashmere"}, Clothing.Size.M, Clothing.Category.women);
        Clothing product2 = new Clothing(2, "Jeans", 80.0, 15, "ModelY", "Blue", 80.0, "B2", new String[]{"Denim"}, Clothing.Size.L, Clothing.Category.men);

       
        Purchase.loadExtent("non_existing_file.xml");

        Purchase p1 = new Purchase(10, Purchase.PaymentMethod.CASH, LocalDate.now(), java.util.Arrays.asList(product1));
        Purchase p2 = new Purchase(11, Purchase.PaymentMethod.CARD, LocalDate.now(), java.util.Arrays.asList(product2));

        Purchase.saveExtent(TEST_FILE);

        Purchase.loadExtent(TEST_FILE);
        List<Purchase> extent = Purchase.getExtent();

        assertEquals(2, extent.size());
        assertEquals(10, extent.get(0).getTransactionID());
        assertEquals(11, extent.get(1).getTransactionID());
    }
}
