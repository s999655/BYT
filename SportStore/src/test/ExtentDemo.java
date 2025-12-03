package test;

import main.person.Manager;
import java.time.LocalDate;

public class ExtentDemo {
    public static void main(String[] args) {

        Manager.loadExtent("managers.xml");

        Manager m = new Manager(
                "John",
                "Doe",
                "john@example.com",
                "+48123456789",
                101,
                LocalDate.of(2020, 1, 1)
        );

        System.out.println("Number of managers: " + Manager.getExtent().size());

        Manager.saveExtent("managers.xml");

        System.out.println("Extent saved.");
    }
}
