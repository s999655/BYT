package test;

import main.person.Staff;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

 
class TestStaff extends Staff {
    public TestStaff(String name, String surname, String email, String phoneNumber,
                     int employeeNumber, LocalDate employmentDate) {
        super(name, surname, email, phoneNumber, employeeNumber, employmentDate);
    }
}

public class testStaff {

    @Test
    void constructor_createsStaffCorrectly() {
        LocalDate date = LocalDate.of(2020, 1, 1);

        TestStaff s = new TestStaff(
                "John", "Doe", "john@example.com", "+48123456789",
                5, date
        );

        assertEquals(5, s.getEmployeeNumber());
        assertEquals(date, s.getEmploymentDate());
    }

    @Test
    void setEmployeeNumber_negative_throwsException() {
        LocalDate date = LocalDate.of(2020, 1, 1);

        assertThrows(IllegalArgumentException.class, () -> {
            new TestStaff("Ann", "Lee", "ann@example.com", "+48123456780",
                    -1, date);
        });
    }

    @Test
    void setEmploymentDate_inFuture_throwsException() {
        LocalDate future = LocalDate.now().plusDays(10);

        assertThrows(IllegalArgumentException.class, () -> {
            new TestStaff("Mark", "Blue", "mark@example.com", "+48123456788",
                    10, future);
        });
    }

    @Test
    void getYearsSinceEmployment_returnsCorrectValue() {
        LocalDate threeYearsAgo = LocalDate.now().minusYears(3);

        TestStaff s = new TestStaff(
                "Anna", "Gray", "anna@example.com", "+48123456701",
                7, threeYearsAgo
        );

        assertEquals(3, s.getYearsSinceEmployment());
    }

    @Test
    void setVacationDays_negative_throwsException() {
        TestStaff s = new TestStaff(
                "John", "Brown", "johnb@example.com", "+48123456222",
                3, LocalDate.of(2019, 1, 1)
        );

        assertThrows(IllegalArgumentException.class, () -> {
            s.setVacationDays(-5);
        });
    }

    @Test
    void setVacationDays_moreThan40_throwsException() {
        TestStaff s = new TestStaff(
                "Maya", "Red", "maya@example.com", "+48123450000",
                8, LocalDate.of(2018, 5, 15)
        );

        assertThrows(IllegalArgumentException.class, () -> {
            s.setVacationDays(10); 
        });
    }

    @Test
    void getVacationDays_returnsStaticValue() {
        TestStaff s = new TestStaff(
                "Alex", "White", "alex@example.com", "+48123451111",
                12, LocalDate.of(2020, 2, 2)
        );

        assertEquals(25, s.getVacationDays());
    }
}
