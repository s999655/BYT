package main.person;

import java.io.Serializable;

public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    private String street;
    private int buildingNumber;
    private Integer apartmentNumber;
    private String city;

    private static List<Address> extent = new ArrayList<>();

    public static List<Address> getExtent() {
          return Collections.unmodifiableList(extent);
    }

    private static void addToExtent(Address address) {
        extent.add(address);
    }

    public Address() {
    }

    public Address(String street, int buildingNumber, Integer apartmentNumber, String city) {
        setStreet(street);
        setBuildingNumber(buildingNumber);
        setApartmentNumber(apartmentNumber);
        setCity(city);

      addToExtent(this);
    }

    public String getStreet() {
        return street;
    }

    public int getBuildingNumber() {
        return buildingNumber;
    }

    public Integer getApartmentNumber() {
        return apartmentNumber;
    }

    public String getCity() {
        return city;
    }

    public void setStreet(String street) {
        if (street == null || street.trim().isEmpty()) {
            throw new IllegalArgumentException("Street cannot be empty");
        }
        this.street = street.trim();
    }

    public void setBuildingNumber(int buildingNumber) {
        if (buildingNumber <= 0) {
            throw new IllegalArgumentException("Building number must be positive");
        }
        this.buildingNumber = buildingNumber;
    }

    public void setApartmentNumber(Integer apartmentNumber) {
        if (apartmentNumber != null && apartmentNumber <= 0) {
            throw new IllegalArgumentException("Apartment number must be positive if provided");
        }
        this.apartmentNumber = apartmentNumber;
    }

    public void setCity(String city) {
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("City cannot be empty");
        }
        this.city = city.trim();
    }

    public static void saveExtent(String fileName) {
      try (XMLEncoder encoder = new XMLEncoder(
              new BufferedOutputStream(new FileOutputStream(fileName)))) {
          encoder.writeObject(extent);
      } catch (Exception e) {
          throw new RuntimeException("Error saving Address extent", e);
      }
    }

    @SuppressWarnings("unchecked")
    public static void loadExtent(String fileName) {
        try (XMLDecoder decoder = new XMLDecoder(
                new BufferedInputStream(new FileInputStream(fileName)))) {
            extent = (List<Address>) decoder.readObject();
        } catch (FileNotFoundException e) {
            extent = new ArrayList<>();
        }
    }

    @Override
    public String toString() {
        String apt = (apartmentNumber != null) ? ("/" + apartmentNumber) : "";
        return street + " " + buildingNumber + apt + ", " + city;
    }
}
