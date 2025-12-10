package main.person;

import java.time.LocalDate;

public abstract class Staff extends Person {

    private static final long serialVersionUID = 1L;
    
    private int employeeNumber;
    private LocalDate employmentDate;
    private static int vacationDays = 25;

    public Staff(){
        super();
    }
    
    public Staff(String name, String surname, String email, String phoneNumber, int employeeNumber, LocalDate employmentDate) {
        super(name, surname, email, phoneNumber);
        setEmployeeNumber(employeeNumber);
        setEmploymentDate(employmentDate);
    }

    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public LocalDate getEmploymentDate() {
        return employmentDate;
    }

    public int getYearsSinceEmployment() { //derived attribute
        LocalDate currentDate = LocalDate.now();
        return currentDate.getYear() - employmentDate.getYear();
    }
    
    public int getVacationDays(){
        return Staff.vacationDays;
    }

    public void setEmployeeNumber(int employeeNumber) {
        if (employeeNumber <= 0) {
            throw new IllegalArgumentException("Employee number must be positive");
        }
        this.employeeNumber = employeeNumber;
    }

    public void setEmploymentDate( LocalDate employmentDate) {
        if (employmentDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Employment date cannot be in the future");
        }
        this.employmentDate = employmentDate;
    }



    public void setVacationDays(int vacationDays) {
        if (vacationDays < 0) {
            throw new IllegalArgumentException("Vacation days cannot be negative");
        } else if (vacationDays < 40) {
            throw new IllegalArgumentException("Vacation day cannot be more than 40 a year");
        }
        Staff.vacationDays = vacationDays;
    }

}
