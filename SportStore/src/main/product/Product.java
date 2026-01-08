package main.product;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Product implements Serializable{

    private static final long serialVersionUID = 1L;

    private int productID;
    private String name;
    private double price;
    private int stockQuantity;
    private String model;
    private String color;
    private double minPrice;
    private String location;

    public Product(){
    }
    
    public Product(int productID, String name, double price, int stockQuantity, String model, String color, double minPrice, String location){
        setProductID(productID);
        setName(name);
        setPrice(price);
        setStockQuantity(stockQuantity);
        setModel(model);
        setColor(color);
        setMinPrice(minPrice);
        setLocation(location);
    }



    //getters
    public int getProductID(){
        return productID;
    }
    public String getName(){
        return name;
    }
    public double getPrice(){
        return price;
    }
    public int getStockQuantity(){
        return stockQuantity;
    }
    public String getModel(){
        return model;
    }
    public String getColor(){
        return color;
    }
    public double getMinPrice(){
        return minPrice;
    }
    public String getLocation(){
        return location;
    }

    //setters
    public void setProductID(int productID){
        if (productID <= 0)
            throw new IllegalArgumentException("ProductID cannot be negative");
        this.productID = productID;
    }

    public void setName(String name){
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("name cannot be null or empty");
        }
        this.name = name;
    }

    public void setPrice(double price){
        if (price == 0){
            throw new IllegalArgumentException("price cannot be zero");
        }else if(price <= this.minPrice)
            throw new IllegalArgumentException("price cannot be less than minimum price");
        this.price = price;
    }

    public void setStockQuantity(int stockQuantity){
        if (stockQuantity < 0){
            throw new IllegalArgumentException("stockQuantity cannot be negative");
        }
        this.stockQuantity = stockQuantity;
    }

    public void setModel(String model){
        if(model == null || model.isEmpty()){
            throw new IllegalArgumentException("model cannot be null or empty");
        }
        this.model = model;
    }

    public void setColor(String color){
        if(color == null || color.isEmpty()){
            throw new IllegalArgumentException("color cannot be null or empty");
        }
        this.color = color;
    }

    public void setMinPrice(double minPrice){
        if(minPrice <= 0){
            throw new IllegalArgumentException("minPrice must be greater than zero");
        }
        this.minPrice = minPrice;
    }


    public void setLocation(String location){
        if(location == null || location.isEmpty()){
            throw new IllegalArgumentException("location cannot be null or empty");
        }
        this.location = location;

    }

    //this product may CONTAIN other products
    private final List<Product> contains = new ArrayList<>();

    //this product may be CONTAINED IN other products
    private final List<Product> containedIn = new ArrayList<>();

    public List<Product> getContains() {
        return Collections.unmodifiableList(contains);
    }

    public List<Product> getContainedIn() {
        return Collections.unmodifiableList(containedIn);
    }

    public void addContainedProduct(Product component) {
        if (component == null) {
            throw new IllegalArgumentException("Contained product cannot be null");
        }
        if (component == this) {
            throw new IllegalArgumentException("A product cannot contain itself");
        }
        if (contains.contains(component)) {
            return; //already linked
        }

        contains.add(component);

        if (!component.containedIn.contains(this)) {
            component.containedIn.add(this);
        }
    }

    public void removeContainedProduct(Product component) {
        if (component == null) return;

        if (contains.remove(component)) {
            component.containedIn.remove(this);
        }
    }
}
