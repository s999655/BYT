package main.person;
import java.time.LocalDate;

public class Manager extends Staff {

    private LocalDate promotionDate;

    public Manager(String name, String surname, String email, String phoneNumber, int employeeNumber, LocalDate employmentDate) {
        super(name, surname, email, phoneNumber, employeeNumber, employmentDate);
    }
    
    //for the optional attribute promotionDate
    public Manager(String name, String surname, String email, String phoneNumber, int employeeNumber, LocalDate employmentDate, LocalDate promotionDate) {
        this(name, surname, email, phoneNumber, employeeNumber, employmentDate);
        setPromotionDate(promotionDate);
    }


    public LocalDate getPromotionDate(){
        return this.promotionDate;
    }
    
    public void setPromotionDate(LocalDate promotionDate){
        if (promotionDate.isBefore(getEmploymentDate())) {
            throw new IllegalArgumentException("Promotion date cannot be before employment date");
        }
        this.promotionDate = promotionDate;
    }

}
