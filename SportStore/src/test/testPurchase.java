package test;

import main.purchase.Purchase;
import main.product.Product;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class testPurchase {

    private static final String TEST_FILE = "test_purchases.xml";

    
    static class DummyProduct extends Product {
        public DummyProduct(int id, String name, double price) {
            super(id, name, price, 10, "model", "color", 5, "loc");
        }
    }

   
    static class DummyPromotion extends Promotion {
        private final double rate;
        public DummyPromotion(double rate) { this.rate = rate; }
        @Override
        public double getDiscountRate() { return rate; }
    }

    @Test
    void constructorWithoutDiscount_createsPurchaseCorrectly() {
        DummyProduct product = new DummyProduct(1, "Prod1", 100.0);
        Purchase p = new Purchase(1, Purchase.PaymentMethod.CASH, LocalDate.now(), product);

        assertEquals(1, p.getTransactionID());
        assertEquals(Purchase.PaymentMethod.CASH, p.getPaymentMethod());
        assertEquals(100.0, p.getFinalPrice());
        assertTrue(Purchase.getExtent().contains(p));
    }

    @Test
    void constructorWithDiscount_createsPurchaseCorrectly() {
        DummyProduct product = new DummyProduct(2, "Prod2", 200.0);
        DummyPromotion promo = new DummyPromotion(0.1); // 10% discount

        Purchase p = new Purchase(2, Purchase.PaymentMethod.CARD, LocalDate.now(), product, promo);

        assertEquals(180.0, p.getFinalPrice()); // 200 - 10%
        assertTrue(Purchase.getExtent().contains(p));
    }

    @Test
    void setTransactionID_invalid_throwsException() {
        DummyProduct product = new DummyProduct(3, "Prod3", 50.0);
        Purchase p = new Purchase(1, Purchase.PaymentMethod.CASH, LocalDate.now(), product);

        Exception e = assertThrows(IllegalArgumentException.class, () -> p.setTransactionID(0));
        assertEquals("transactionID must be positive", e.getMessage());
    }

    @Test
    void setPaymentMethod_null_throwsException() {
        DummyProduct product = new DummyProduct(4, "Prod4", 50.0);
        Purchase p = new Purchase(1, Purchase.PaymentMethod.CASH, LocalDate.now(), product);

        Exception e = assertThrows(IllegalArgumentException.class, () -> p.setPaymentMethod(null));
        assertEquals("paymentMethod cannot be null", e.getMessage());
    }

    @Test
    void setPurchaseDate_nullOrFuture_throwsException() {
        DummyProduct product = new DummyProduct(5, "Prod5", 50.0);
        Purchase p = new Purchase(1, Purchase.PaymentMethod.CASH, LocalDate.now(), product);

        assertThrows(IllegalArgumentException.class, () -> p.setPurchaseDate(null));
        assertThrows(IllegalArgumentException.class, () -> p.setPurchaseDate(LocalDate.now().plusDays(1)));
    }

    @Test
    void extentPersistence_saveAndLoadRestoresPurchases() {
        DummyProduct product1 = new DummyProduct(6, "Prod6", 120.0);
        DummyProduct product2 = new DummyProduct(7, "Prod7", 80.0);

       
        Purchase.loadExtent("non_existing_file.xml");

        Purchase p1 = new Purchase(10, Purchase.PaymentMethod.CASH, LocalDate.now(), product1);
        Purchase p2 = new Purchase(11, Purchase.PaymentMethod.CARD, LocalDate.now(), product2);

        Purchase.saveExtent(TEST_FILE);

        Purchase.loadExtent(TEST_FILE);
        List<Purchase> extent = Purchase.getExtent();

        assertEquals(2, extent.size());
        assertEquals(10, extent.get(0).getTransactionID());
        assertEquals(11, extent.get(1).getTransactionID());
    }
}
