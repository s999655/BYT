package main.purchase;

import java.io.Serializable;
import main.product.Product;

public class ProductsQuantityInPurchase implements Serializable {

    private static final long serialVersionUID = 1L;

    private Purchase purchase;
    private Product product;
    private int quantity;

    public ProductsQuantityInPurchase() {
    }

    public ProductsQuantityInPurchase(Purchase purchase, Product product, int quantity) {
        setPurchase(purchase);
        setProduct(product);
        setQuantity(quantity);
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setPurchase(Purchase purchase) {
        if (purchase == null) throw new IllegalArgumentException("Purchase cannot be null");
        this.purchase = purchase;
    }

    public void setProduct(Product product) {
        if (product == null) throw new IllegalArgumentException("Product cannot be null");
        this.product = product;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        this.quantity = quantity;
    }
}
