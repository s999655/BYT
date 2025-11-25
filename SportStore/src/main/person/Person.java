package main.person;

public abstract class Person {
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;

public Person(String name, String surname, String email, String phoneNumber) {
    if (name == null || name.isEmpty()) {
        throw new IllegalArgumentException("Name cannot be null or empty");
    }else { this.name = name; }

    if (surname == null || surname.isEmpty()) {
        throw new IllegalArgumentException("Surname cannot be null or empty");
    } else { this.surname = surname; }

    if (email == null || email.isEmpty() || !email.contains("@")) {
        throw new IllegalArgumentException("Email cannot be null or empty, and must include '@'");
    } else { this.email = email; }

    if (phoneNumber == null || phoneNumber.isEmpty()) {
        throw new IllegalArgumentException("Phone number cannot be null or empty");
    } else { this.phoneNumber = phoneNumber; }

    if (!phoneNumber.matches("\\+?\\d+")) {
        throw new IllegalArgumentException("Phone number must contain only digits and may start with '+'");
    }

}

}