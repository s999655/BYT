package test;

import main.person.Person;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class testPerson {

    
    static class TestPerson extends Person {
        public TestPerson() { super(); }
        public TestPerson(String name, String surname, String email, String phone) {
            super(name, surname, email, phone);
        }
    }

    @Test
    void constructor_setsFieldsCorrectly() {
        TestPerson p = new TestPerson("Alice", "Smith", "alice@example.com", "+123456789");

        assertEquals("Alice", p.getName());
        assertEquals("Smith", p.getSurname());
        assertEquals("alice@example.com", p.getEmail());
        assertEquals("+123456789", p.getPhoneNumber());
    }

    @Test
    void setName_nullOrEmpty_throwsException() {
        TestPerson p = new TestPerson();
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> p.setName(null));
        assertEquals("Name cannot be null or empty", e1.getMessage());

        Exception e2 = assertThrows(IllegalArgumentException.class, () -> p.setName(""));
        assertEquals("Name cannot be null or empty", e2.getMessage());
    }

    @Test
    void setSurname_nullOrEmpty_throwsException() {
        TestPerson p = new TestPerson();
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> p.setSurname(null));
        assertEquals("Surname cannot be null or empty", e1.getMessage());

        Exception e2 = assertThrows(IllegalArgumentException.class, () -> p.setSurname(""));
        assertEquals("Surname cannot be null or empty", e2.getMessage());
    }

    @Test
    void setEmail_invalid_throwsException() {
        TestPerson p = new TestPerson();

        Exception e1 = assertThrows(IllegalArgumentException.class, () -> p.setEmail(null));
        assertEquals("Email cannot be null or empty, and must include '@'", e1.getMessage());

        Exception e2 = assertThrows(IllegalArgumentException.class, () -> p.setEmail(""));
        assertEquals("Email cannot be null or empty, and must include '@'", e2.getMessage());

        Exception e3 = assertThrows(IllegalArgumentException.class, () -> p.setEmail("alice.example.com"));
        assertEquals("Email cannot be null or empty, and must include '@'", e3.getMessage());
    }

    @Test
    void setPhoneNumber_invalid_throwsException() {
        TestPerson p = new TestPerson();

        Exception e1 = assertThrows(IllegalArgumentException.class, () -> p.setPhoneNumber(null));
        assertEquals("Phone number cannot be null or empty", e1.getMessage());

        Exception e2 = assertThrows(IllegalArgumentException.class, () -> p.setPhoneNumber(""));
        assertEquals("Phone number cannot be null or empty", e2.getMessage());

        Exception e3 = assertThrows(IllegalArgumentException.class, () -> p.setPhoneNumber("123456789"));
        assertEquals("Phone number must contain only digits and must start with '+'", e3.getMessage());

        Exception e4 = assertThrows(IllegalArgumentException.class, () -> p.setPhoneNumber("+12a456"));
        assertEquals("Phone number must contain only digits and must start with '+'", e4.getMessage());
    }

    @Test
    void setters_allowValidValues() {
        TestPerson p = new TestPerson();

        p.setName("Bob");
        p.setSurname("Brown");
        p.setEmail("bob@example.com");
        p.setPhoneNumber("+987654321");

        assertEquals("Bob", p.getName());
        assertEquals("Brown", p.getSurname());
        assertEquals("bob@example.com", p.getEmail());
        assertEquals("+987654321", p.getPhoneNumber());
    }
}
