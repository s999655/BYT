package main.product;

public class Clothing extends Product{
    enum Size{XS,S,M,L,XL,XXL};
    String[] material;
    enum Category{men, women, kids, unisex};
    Category category;
    Size size;

    public Clothing(int productID, String name, double price, int stockQuantity, String model, String color, double minPrice, String location, String[] material, Size size, Category category){
        super(productID, name, price,  stockQuantity, model, color, minPrice, location);
        setSize(size);
        setMaterial(material);
        setCategory(category);
    }

    public void setMaterial(String[] material){
        if(material.length == 0){
            throw new IllegalArgumentException("material cannot be empty");
        }
        this.material = material;
    }

    public void setSize(Size size){
        if(size == null){
            throw new IllegalArgumentException("size cannot be null");
        }
        this.size = size;
    }

    public void setCategory(Category category){
        if(category == null){
            throw new IllegalArgumentException("category cannot be null");
        }
        this.category = category;
    }

    //getters
    
}
