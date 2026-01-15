package main.product;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;


    

public class Brand implements Serializable{

  private static final long serialVersionUID = 1L;

  private static List<Brand> extent = new ArrayList<>();
  
  public static List<Brand> getExtent() {
        return Collections.unmodifiableList(extent);
    }

    private static void addToExtent(Brand brand) {
        if (brand == null) {
            throw new IllegalArgumentException("Brand cannot be null");
        }
        extent.add(brand);
    }

  private String BrandName;
  private LocalDate FoundationDate;
  private HashSet<Product> products = new HashSet<>();


  public Brand(String brandName, LocalDate foundationDate) {
      this.BrandName = setbrandName(brandName);
      this.FoundationDate = setFoundationDate(foundationDate);
      addToExtent(this);

  }
    //getters
    public String getBrandName() {
        return BrandName;
    }

    public LocalDate getFoundationDate() {
        return FoundationDate;
    }

    public HashSet<Product> getProducts() {
        return new HashSet<>(products);
    }

  //setters
  public String setbrandName(String brandName) {
      if (brandName == null || brandName.isEmpty()) {
          throw new IllegalArgumentException("Brand name cannot be null or empty");
      }
      return brandName;
  }
  
  public LocalDate setFoundationDate(LocalDate foundationDate) {
      if (foundationDate == null || foundationDate.isAfter(LocalDate.now())) {
          throw new IllegalArgumentException("Foundation date cannot be null or in the future");
      }
      return foundationDate;
  }

  //association methods
  void addProduct(Product product) {
    if(product == null) {
        throw new IllegalArgumentException("Product cannot be null");
    }
      this.products.add(product);
    }

    void removeProduct(Product product) {//removes the product from the brand's associated products and deletes the product
      if(product == null) {
          throw new IllegalArgumentException("Product cannot be null");
      }
      if (!this.products.contains(product)) {
              throw new IllegalArgumentException("Product not associated with this brand");
          }    

      this.products.remove(product);
      product.delete(); 
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
            extent = (List<Brand>) decoder.readObject();
        } catch (FileNotFoundException e) {
            extent = new ArrayList<>();
        }
    }

}
