package edu.unapec.shoppingorders.models;

public class Client {
   private int id;
   private String name;
   private String lastName;
   private String identificationCard;

   public Client()
   {

   }

    public Client(int id, String name, String lastName, String identificationCard) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.identificationCard = identificationCard;
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
}
