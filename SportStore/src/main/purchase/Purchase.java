package main.purchase;
import main.product.Product;
import java.time.LocalDate;

public class Purchase {
    private int transactionID;
    private double finalPrice;
    enum PaymentMethod {CASH , CARD};
    private PaymentMethod paymentMethod;
    private LocalDate purchaseDate;
    
    //constructor without discount
    public Purchase(int transactionID, PaymentMethod paymentMethod, LocalDate purchaseDate, Product product){
        setTransactionID(transactionID);
        setPaymentMethod(paymentMethod);
        setPurchaseDate(purchaseDate);
        finalPrice = product.getPrice();
    }

    //constructor with discount
    public Purchase(int transactionID, PaymentMethod paymentMethod, LocalDate purchaseDate, Product product, Promotion discount){
        setTransactionID(transactionID);
        setPaymentMethod(paymentMethod);
        setPurchaseDate(purchaseDate);
        finalPrice = product.getPrice()- (product.getPrice()*discount.getDiscountRate());
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
        if(purchaseDate.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("purchaseDate cannot be in the future");
        }
        this.purchaseDate = purchaseDate;
    }

    
    public void applyDiscount(){

    }
}
