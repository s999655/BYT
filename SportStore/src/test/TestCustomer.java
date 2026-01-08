package test;

import main.person.Customer;
import main.person.Address;
import main.purchase.Purchase;
import main.product.Brand;
import main.product.Clothing;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestCustomer {

    private static final String TEST_FILE = "test_customers.xml";

    @BeforeEach
    @AfterEach 
    void cleanup() {
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void constructor_createsCustomerCorrectly() {
        Address addr = new Address("Street 1", 123, 45, "Gotham");
        LocalDate regDate = LocalDate.of(2020, 1, 1);

        Customer c = new Customer(
                "Alice", "Smith", "alice@example.com", "+48123456789",
                "ACC123", regDate, addr);

        assertEquals("ACC123", c.getAccountNumber());
        assertEquals(regDate, c.getRegistrationDate());
        assertEquals(addr, c.getAddress());
        assertEquals(0, c.getLoyaltyPoints());
        assertTrue(c.getPurchaseHistory().isEmpty());
    }

    @Test
    void setAccountNumber_nullOrEmpty_throwsException() {
        Customer c = new Customer();
        assertThrows(IllegalArgumentException.class, () -> c.setAccountNumber(null));
        assertThrows(IllegalArgumentException.class, () -> c.setAccountNumber(""));
    }

    @Test
    void setRegistrationDate_futureDate_throwsException() {
        Customer c = new Customer();
        assertThrows(IllegalArgumentException.class, () -> c.setRegistrationDate(LocalDate.now().plusDays(1)));
    }

    @Test
    void setAddress_null_throwsException() {
        Customer c = new Customer();
        assertThrows(IllegalArgumentException.class, () -> c.setAddress(null));
    }

    @Test
    void updateLoyaltyPoints_worksCorrectly() {
        Customer c = new Customer();
        c.updateLoyaltyPoints(10);
        assertEquals(10, c.getLoyaltyPoints());

        Exception e = assertThrows(IllegalArgumentException.class, () -> c.updateLoyaltyPoints(-5));
        assertEquals("Amount spent cannot be negative", e.getMessage());
    }

    @Test
    void checkDiscount_returnsCorrectValue() {
        Customer c = new Customer();
        c.updateLoyaltyPoints(20);
        assertEquals(1.0, c.checkDiscount());
    }

    @Test
    void addPurchase_and_viewPurchaseHistory_works() {
        Customer c = new Customer("Test", "Testowicz", "test@example.com", "+48111222333",
                "ACC999", LocalDate.of(2021, 5, 15), new Address("Street 1", 123, 45, "Gotham"));
        String[] material1 = {"Cotton"};
        String[] material2 = {"Polyester"};
        Clothing product1 = new Clothing(1, new Brand("TestBrand", java.time.LocalDate.of(2000,1,1)), "Football shirt", 29.99, 100, "ModelX", "White", 19.99, "Aisle 3", material1, Clothing.Size.M, Clothing.Category.men);
        Clothing product2 = new Clothing(2, new Brand("TestBrand", java.time.LocalDate.of(2000,1,1)), "Basketball cap", 49.99, 50, "ModelY", "Black", 39.99, "Aisle 4", material2, Clothing.Size.L, Clothing.Category.unisex);
        Purchase p1 = new Purchase(1, Purchase.PaymentMethod.CARD, LocalDate.now(), java.util.Arrays.asList(product1));
        Purchase p2 = new Purchase(2, Purchase.PaymentMethod.CASH, LocalDate.now(), java.util.Arrays.asList(product2));

        c.addPurchase(p1);
        c.addPurchase(p2);

        HashSet<Purchase> history = c.viewPurchaseHistory();
        assertEquals(2, history.size());
        assertTrue(history.contains(p1));
        assertTrue(history.contains(p2));

        Exception e = assertThrows(IllegalArgumentException.class, () -> c.addPurchase(null));
        assertEquals("Purchase cannot be null", e.getMessage());
    }

    @Test
    void extentPersistence_saveAndLoadRestoresCustomers() {

        Customer.loadExtent("non_existing_file.xml");

        Customer c1 = new Customer("A", "B", "a@b.com", "+123456789", "ACC1", LocalDate.of(2020,1,1), new Address());
        Customer c2 = new Customer("C", "D", "c@d.com", "+987654321", "ACC2", LocalDate.of(2021,1,1), new Address());

        Customer.saveExtent(TEST_FILE);

        Customer.loadExtent(TEST_FILE);
        List<Customer> extent = Customer.getExtent();

        assertEquals(2, extent.size());

        assertEquals("ACC1", extent.get(0).getAccountNumber());
        assertEquals("ACC2", extent.get(1).getAccountNumber());
    }
}
