package main.purchase;

import main.product.Product;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

public class Purchase implements Serializable{
    private static final long serialVersionUID = 1L;

    private static List<Purchase> extent = new ArrayList<>();

    public Purchase() {
    }

    public static List<Purchase> getExtent() {
        return Collections.unmodifiableList(extent);
    }

    private static void addToExtent(Purchase p) {
        if (p == null) throw new IllegalArgumentException("Purchase cannot be null");
        extent.add(p);
    }
    
    private int transactionID;
    private double finalPrice;
    public enum PaymentMethod {CASH , CARD};
    private PaymentMethod paymentMethod;
    private LocalDate purchaseDate;

    
    //constructor without discount
    public Purchase(int transactionID, PaymentMethod paymentMethod, LocalDate purchaseDate, Product product){
        setTransactionID(transactionID);
        setPaymentMethod(paymentMethod);
        setPurchaseDate(purchaseDate);
        finalPrice = product.getPrice();

        addToExtent(this);
    }
    
    //constructor with discount
    public Purchase(int transactionID, PaymentMethod paymentMethod, LocalDate purchaseDate, Product product, Promotion discount){
        setTransactionID(transactionID);
        setPaymentMethod(paymentMethod);
        setPurchaseDate(purchaseDate);
        finalPrice = product.getPrice()- (product.getPrice()*discount.getDiscountRate());

        addToExtent(this);
    }

    public void setTransactionID(int transactionID){
        if(transactionID <= 0){
            throw new IllegalArgumentException("transactionID must be positive");
        }
        this.transactionID = transactionID;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod){
        if(paymentMethod == null){
            throw new IllegalArgumentException("paymentMethod cannot be null");
        }
        this.paymentMethod = paymentMethod;
    }

    public void setPurchaseDate(LocalDate purchaseDate){
        if(purchaseDate == null){
            throw new IllegalArgumentException("purchaseDate cannot be null");
        }
        if(purchaseDate.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("purchaseDate cannot be in the future");
        }
        this.purchaseDate = purchaseDate;
    }

    
    public void applyDiscount(Promotion discount){
        this.finalPrice = finalPrice - (finalPrice * discount.getDiscountRate()); 
    }

    public static void saveExtent(String fileName) {
        try (XMLEncoder encoder =
                     new XMLEncoder(new BufferedOutputStream(new FileOutputStream(fileName)))) {
            encoder.writeObject(extent);
        } catch (IOException e) {
            throw new RuntimeException("Error saving Purchase extent", e);
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadExtent(String fileName) {
        try (XMLDecoder decoder =
                     new XMLDecoder(new BufferedInputStream(new FileInputStream(fileName)))) {
            extent = (List<Purchase>) decoder.readObject();
        } catch (FileNotFoundException e) {
            extent = new ArrayList<>();
        }
    }

    //getters
    public int getTransactionID(){
        return transactionID;
    }

    public double getFinalPrice(){
        return finalPrice;
    }

    public PaymentMethod getPaymentMethod(){
        return paymentMethod;
    }

    public LocalDate getPurchaseDate(){
        return purchaseDate;
    }

}
