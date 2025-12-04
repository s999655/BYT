package test;

import main.person.Customer;
import main.purchase.Purchase;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class testCustomer {

    private static final String TEST_FILE = "test_customers.xml";

   
    static class Address {
        String street = "Street 1";
        String city = "City";
        String zip = "12345";
        public Address() {}
    }

    
    static class DummyPurchase extends Purchase {
        public DummyPurchase() {
            super();
        }
    }

    @Test
    void constructor_createsCustomerCorrectly() {
        Address addr = new Address();
        LocalDate regDate = LocalDate.of(2020, 1, 1);

        Customer c = new Customer(
                "Alice", "Smith", "alice@example.com", "+48123456789",
                "ACC123", regDate, "aliceLogin", "password123", addr
        );

        assertEquals("ACC123", c.getAccountNumber());
        assertEquals(regDate, c.getRegistrationDate());
        assertEquals("aliceLogin", c.getLogin());
        assertEquals("password123", c.getPassword());
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
    void setLogin_nullOrEmpty_throwsException() {
        Customer c = new Customer();
        assertThrows(IllegalArgumentException.class, () -> c.setLogin(null));
        assertThrows(IllegalArgumentException.class, () -> c.setLogin(""));
    }

    @Test
    void setPassword_tooShort_throwsException() {
        Customer c = new Customer();
        assertThrows(IllegalArgumentException.class, () -> c.setPassword("123"));
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
        Customer c = new Customer();
        Purchase p1 = new DummyPurchase();
        Purchase p2 = new DummyPurchase();

        c.addPurchase(p1);
        c.addPurchase(p2);

        List<Purchase> history = c.viewPurchaseHistory();
        assertEquals(2, history.size());
        assertTrue(history.contains(p1));
        assertTrue(history.contains(p2));

        Exception e = assertThrows(IllegalArgumentException.class, () -> c.addPurchase(null));
        assertEquals("Purchase cannot be null", e.getMessage());
    }

    @Test
    void extentPersistence_saveAndLoadRestoresCustomers() {

        Customer.loadExtent("non_existing_file.xml");

        Customer c1 = new Customer("A", "B", "a@b.com", "+123456789", "ACC1", LocalDate.of(2020,1,1), "login1", "pass123", new Address());
        Customer c2 = new Customer("C", "D", "c@d.com", "+987654321", "ACC2", LocalDate.of(2021,1,1), "login2", "pass456", new Address());

        Customer.saveExtent(TEST_FILE);

        Customer.loadExtent(TEST_FILE);
        var extent = Customer.getExtent();

        assertEquals(2, extent.size());
        assertEquals("ACC1", extent.get(0).getAccountNumber());
        assertEquals("ACC2", extent.get(1).getAccountNumber());
    }
}
