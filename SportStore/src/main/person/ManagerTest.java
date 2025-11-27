package main.person;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ManagerTest{
      private static final String TEST_FILE = "test_managers.xml";
}

@Test  //creating a valid manager
void constructor_createsManagerAndAddsToExtent() {
    LocalDate employmentDate = LocalDate.of(2020, 1, 1);

    Manager m = new Manager(
            "+Alice+", "Smith", "alice@example.com", "+48123456789",
            1, employmentDate
    );

    assertEquals(employmentDate, m.getEmploymentDate());
    assertTrue(Manager.getExtent().contains(m));
}

@Test  //promotion date cannot be before employment date
void setPromotionDate_beforeEmployment_throwsException() {
    LocalDate employmentDate = LocalDate.of(2020, 1, 1);
    Manager m = new Manager(
            "Alice", "Smith", "alice@example.com", "+48123456789",
            1, employmentDate
    );

    LocalDate invalidPromotionDate = LocalDate.of(2019, 12, 31);

    IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> m.setPromotionDate(invalidPromotionDate)
    );

    assertEquals("Promotion date cannot be before employment date", ex.getMessage());
}

@Test  //promotion date can be null
void setPromotionDate_nullIsAllowed() {
    LocalDate employmentDate = LocalDate.of(2020, 1, 1);
    Manager m = new Manager(
            "Alice", "Smith", "alice@example.com", "+48123456789",
            1, employmentDate
    );

    assertDoesNotThrow(() -> m.setPromotionDate(null));
    assertNull(m.getPromotionDate());
}

@Test
void constructor_withPromotionDate_setsPromotionDateAndAddsToExtent() {
    Manager.loadExtent(TEST_FILE);

    LocalDate employmentDate = LocalDate.of(2020, 1, 1);
    LocalDate promotionDate = LocalDate.of(2022, 6, 15);

    Manager m = new Manager(
            "Alice",
            "Smith",
            "alice@example.com",
            "+48123456789",
            1,
            employmentDate,
            promotionDate
    );

    assertEquals(promotionDate, m.getPromotionDate());
    assertTrue(Manager.getExtent().contains(m));
}

@Test
void getYearsSinceEmployment_returnsCorrectValue() {
    Manager.loadExtent(TEST_FILE);

    LocalDate threeYearsAgo = LocalDate.now().minusYears(3);

    Manager m = new Manager(
            "Bob",
            "Brown",
            "bob@example.com",
            "+48123456780",
            2,
            threeYearsAgo
    );

    int years = m.getYearsSinceEmployment();

    assertEquals(3, years);
}

@Test
void constructor_withInvalidEmployeeNumber_throwsException() {
    LocalDate employmentDate = LocalDate.of(2020, 1, 1);

    assertThrows(IllegalArgumentException.class, () -> new Manager(
            "Carol",
            "Johnson",
            "carol@example.com",
            "+48123456781",
            0,                    // invalid
            employmentDate
    ));
}

@Test
void constructor_withInvalidPhoneNumber_throwsException() {
    LocalDate employmentDate = LocalDate.of(2020, 1, 1);

    assertThrows(IllegalArgumentException.class, () -> new Manager(
            "David",
            "White",
            "david@example.com",
            "123456789",          // invalid, no '+'
            3,
            employmentDate
    ));
}


@Test  //extent stores created managers
void extent_containsAllCreatedManagers() {
    Manager.loadExtent(TEST_FILE); 

    LocalDate d = LocalDate.of(2020, 1, 1);

    Manager m1 = new Manager("Alice", "Smith", "alice@example.com", "+48123456789", 1, d);
    Manager m2 = new Manager("Bob", "Brown", "bob@example.com", "+48123456780", 2, d);

    List<Manager> extent = Manager.getExtent();

    assertEquals(2, extent.size());
    assertTrue(extent.contains(m1));
    assertTrue(extent.contains(m2));
}

@Test  //encapsulation test
void extent_getExtentReturnsUnmodifiableCopy() {
    Manager.loadExtent(TEST_FILE);
    LocalDate d = LocalDate.of(2020, 1, 1);

    Manager m1 = new Manager("Alice", "Smith", "alice@example.com", "+48123456789", 1, d);

    List<Manager> extent = Manager.getExtent();

    assertThrows(UnsupportedOperationException.class, () -> {
        extent.clear();
    });

    assertEquals(1, Manager.getExtent().size());
    assertTrue(Manager.getExtent().contains(m1));
}

@Test  //testing for extent persistence
void extentPersistence_saveAndLoadRestoresManagers() {
    Manager.loadExtent(TEST_FILE);

    LocalDate d = LocalDate.of(2020, 1, 1);

    Manager m1 = new Manager("Alice", "Smith", "alice@example.com", "+48123456789", 1, d);
    Manager m2 = new Manager("Bob", "Brown", "bob@example.com", "+48123456780", 2, d);

    Manager.saveExtent(TEST_FILE);

    Manager.loadExtent(TEST_FILE);

    List<Manager> extent = Manager.getExtent();

    assertEquals(2, extent.size());

    Manager loaded1 = extent.get(0);
    Manager loaded2 = extent.get(1);

    assertEquals(1, loaded1.getEmployeeNumber());
    assertEquals(2, loaded2.getEmployeeNumber());
}
