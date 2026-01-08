package main.product;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Clothing extends Product {
    private static final long serialVersionUID = 1L;

    private static List<Clothing> extent = new ArrayList<>();

    public static List<Clothing> getExtent() {
        return Collections.unmodifiableList(extent);
    }

    private static void addToExtent(Clothing clothing) {
        if (clothing == null) {
            throw new IllegalArgumentException("Clothing instance cannot be null");
        }
        extent.add(clothing);
    }
    
    public enum Size{XS,S,M,L,XL,XXL};
    String[] material;
    public enum Category{men, women, kids, unisex};
    Category category;
    Size size;
    
    //Constructors
    public Clothing(){
    }
    
    public Clothing(int productID, Brand brand, String name, double price, int stockQuantity, String model, String color, double minPrice, String location, String[] material, Size size, Category category){
        super(productID, brand, name, price,  stockQuantity, model, color, minPrice, location);
        setSize(size);
        setMaterial(material);
        setCategory(category);

        addToExtent(this);
    }

    // Setters
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
    public String[] getMaterial(){
        return material;
    }

    public Size getSize(){
        return size;
    }
    
    public Category getCategory(){
        return category;
    }



    public static void saveExtent(String fileName) {
        try (XMLEncoder encoder =
                     new XMLEncoder(new BufferedOutputStream(new FileOutputStream(fileName)))) {
            encoder.writeObject(extent);
        } catch (IOException e) {
            throw new RuntimeException("Error saving Clothing extent", e);
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadExtent(String fileName) {
        try (XMLDecoder decoder =
                     new XMLDecoder(new BufferedInputStream(new FileInputStream(fileName)))) {
            extent = (List<Clothing>) decoder.readObject();
        } catch (FileNotFoundException e) {
            extent = new ArrayList<>();
        }
    }
}
