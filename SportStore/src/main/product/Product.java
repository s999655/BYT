package main.product;

public class Product {
    private int productID;
    private String name;
    private double price;
    private int stockQuantity;
    private String model;
    private String color;
    private double minPrice;
    private String location;

    
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


}
