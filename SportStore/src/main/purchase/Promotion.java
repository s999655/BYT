package main.purchase;

import java.time.LocalDate;

public class Promotion {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private float discountRate;

    public Promotion(String name, LocalDate startDate, LocalDate endDate, float discountRate){
        setName(name);
        setStartDate(startDate);
        setEndDate(endDate);
        setDiscountRate(discountRate);
    }

    //setters
    public void setName(String name){
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("name cannot be null or empty");
        }
        this.name = name;
    }

    public void setStartDate(LocalDate startDate){
        if(startDate == null){
            throw new IllegalArgumentException("startDate cannot be null");
        }else if(startDate.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("startDate cannot be in the past");
        }
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate){
        if(endDate == null){
            throw new IllegalArgumentException("endDate cannot be null");
        }else if(endDate.isBefore(startDate)){
            throw new IllegalArgumentException("endDate cannot be before startDate");
        }
        this.endDate = endDate;
    }

    public void setDiscountRate(float discountRate){
        if(discountRate < 0 || discountRate > 1){
            throw new IllegalArgumentException("discountRate must be between 0 and 1");
        }
        this.discountRate = discountRate;
    }

    //getters
    public String getName(){
        return name;
    }
    public LocalDate getStartDate(){
        return startDate;
    }
    public LocalDate getEndDate(){
        return endDate; 
    }

    public float getDiscountRate(){
        return discountRate;
    }


}
