package main.product;

import java.time.LocalDate;
import java.util.HashSet;

public class Brand {
  private String BrandName;
  private LocalDate FoundationDate;
  private HashSet<Product> products = new HashSet<>();


  public Brand(String brandName, LocalDate foundationDate) {
      this.BrandName = setbrandName(brandName);
      this.FoundationDate = setFoundationDate(foundationDate);

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


}
