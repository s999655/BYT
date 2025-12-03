package main.product;

public class Footwear extends Clothing {

    private float EUsize;

    public Footwear(int productID, String name, double price, int stockQuantity, String model, String color, double minPrice, String location, String[] material, float size, Category category){
        super(productID,  name,  price,  stockQuantity,  model, color, minPrice, location, material, Size.M, category);
        setSize(size); 
    }

    public void setSize(float size){
        if(size == 0)
            throw new IllegalArgumentException("size must be in EU format and cannot be zero");
        else if(size < 25 || size > 50)
            throw new IllegalArgumentException("size must be in EU format and between 25 and 50");
        this.EUsize = size;
    }

}
