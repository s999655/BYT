package main.person;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

public class Manager extends Staff {

    private static final long serialVersionUID = 1L;
    
    private static List<Manager> extent = new ArrayList<>();

    public static List<Manager> getExtent() {
        return Collections.unmodifiableList(extent);
    }

    private static void addToExtent(Manager manager) {
        if (manager == null) {
            throw new IllegalArgumentException("Manager cannot be null");
        }
        extent.add(manager);
    }
    
    private LocalDate promotionDate;

    public Manager() {
    }
    
    public Manager(String name, String surname, String email, String phoneNumber, int employeeNumber, LocalDate employmentDate) {
        super(name, surname, email, phoneNumber, employeeNumber, employmentDate);
        addToExtent(this);
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
        if (promotionDate != null && promotionDate.isBefore(getEmploymentDate())) {
            throw new IllegalArgumentException("Promotion date cannot be before employment date");
        }
        this.promotionDate = promotionDate;
    }

    public static void saveExtent(String fileName) {
        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(fileName)))) {
            encoder.writeObject(extent);
        } catch (IOException e) {
            throw new RuntimeException("Error saving Manager extent to XML", e);
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadExtent(String fileName) {
        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(fileName)))) {
            extent = (List<Manager>) decoder.readObject();
        } catch (FileNotFoundException e) {
            extent = new ArrayList<>();
        }
    }
}
