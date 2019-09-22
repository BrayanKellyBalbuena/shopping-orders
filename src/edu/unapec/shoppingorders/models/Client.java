package edu.unapec.shoppingorders.models;

public class Client {
   private int id;
   private String name;
   private String lastName;
   private String identificationCard;
   private String createdDate;

   public Client()
   {

   }

    public Client(int id, String name, String lastName, String identificationCard, String createdDate) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.identificationCard = identificationCard;
        this.createdDate = createdDate;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdentificationCard() {
        return identificationCard;
    }

    public void setIdentificationCard(String identificationCard) {
        this.identificationCard = identificationCard;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
