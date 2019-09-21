package edu.unapec.shoppingorders.models;

import java.util.Calendar;
import java.util.Date;

public class ShoppingOrder {
    private int id;
    private int clientId;
    private String clientName;
    private Product product;
    private String orderDate;
    private double quantity;
    private double subTotal;

    public ShoppingOrder() { }

    public ShoppingOrder(int id, int clientId, String clientName,
                         Product product, String orderDate, double quantity,
                         double subTotal) {
        this.id = id;
        this.clientId = clientId;
        this.clientName = clientName;
        this.product = product;
        this.orderDate = orderDate;
        this.quantity = quantity;
        this.subTotal = subTotal;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClientName() {
        return this.clientName;
    }

    public void getClientName(String clientName) {
        this.clientName = clientName;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }
}
