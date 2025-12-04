package main.person;
import main.product.*;
import main.purchase.Purchase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;

public class Customer extends Person implements Serializable{

    private static final long serialVersionUID = 1L;

    public static List<Customer> getExtent() {
        return Collections.unmodifiableList(extent);
    }

    private static void addToExtent(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        extent.add(customer);
    }
    
    private String accountNumber;
    private LocalDate registrationDate;
    private String login;
    private String password;
    private Address address;           
    private int loyaltyPoints;        
    private List<Purchase> purchaseHistory; 

    public Customer() {
        super();
        this.purchaseHistory = new ArrayList<>();
    }
    
    public Customer(String name, String surname, String email, String phoneNumber,
                    String accountNumber, LocalDate registrationDate,
                    String login, String password, Address address) {

        super(name, surname, email, phoneNumber);

        setAccountNumber(accountNumber);
        setRegistrationDate(registrationDate);
        setLogin(login);
        setPassword(password);
        setAddress(address);

        this.loyaltyPoints = 0;   
        this.purchaseHistory = new ArrayList<>();
    }


    public String getAccountNumber() { return accountNumber; }
    public LocalDate getRegistrationDate() { return registrationDate; }
    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public Address getAddress() { return address; }
    public int getLoyaltyPoints() { return loyaltyPoints; }
    public List<Purchase> getPurchaseHistory() { return new ArrayList<>(purchaseHistory); }


    public void setAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be empty");
        }
        this.accountNumber = accountNumber;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        if (registrationDate == null || registrationDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Registration date cannot be in the future");
        }
        this.registrationDate = registrationDate;
    }

    public void setLogin(String login) {
        if (login == null || login.isEmpty()) {
            throw new IllegalArgumentException("Login cannot be empty");
        }
        this.login = login;
    }

   public void setPassword(String password) {
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long");
        }
        this.password = password;
    }

    public void setAddress(Address address) {
        if (address == null) {
            throw new IllegalArgumentException("Address cannot be null");
        }
        this.address = address;
    }



public double checkDiscount() {
    return loyaltyPoints * 0.05;
}

 
public void updateLoyaltyPoints(double amountSpent) {
    if (amountSpent < 0) {
        throw new IllegalArgumentException("Amount spent cannot be negative");
    }

    loyaltyPoints += (int) amountSpent;
    }

 
    public List<Purchase> viewPurchaseHistory() {
        return new ArrayList<>(purchaseHistory);
    }

  
    public void addPurchase(Purchase purchase) {
        if (purchase == null) {
            throw new IllegalArgumentException("Purchase cannot be null");
        }
        purchaseHistory.add(purchase);
    }

    public static void saveExtent(String fileName) {
        try (XMLEncoder encoder =
                     new XMLEncoder(new BufferedOutputStream(new FileOutputStream(fileName)))) {
            encoder.writeObject(extent);
        } catch (IOException e) {
            throw new RuntimeException("Error saving Customer extent to XML", e);
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadExtent(String fileName) {
        try (XMLDecoder decoder =
                     new XMLDecoder(new BufferedInputStream(new FileInputStream(fileName)))) {
            extent = (List<Customer>) decoder.readObject();
        } catch (FileNotFoundException e) {
            extent = new ArrayList<>();
        }
    }
}
