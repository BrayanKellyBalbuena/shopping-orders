package edu.unapec.shoppingorders.models;

public class Product {
   private int id;
   private String name;
   private double price;
   private String createdDate;

   public Product() {}

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product(int id, String name, double price, String createdDate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.createdDate = createdDate;
    }

    public int getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
