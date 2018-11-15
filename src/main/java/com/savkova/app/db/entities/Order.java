package com.savkova.app.db.entities;


import java.sql.Date;

public class Order {
    private int id;
    private int contractNumber;
    private int productId;
    private int customerId;
    private Date orderDate;
    private int quantity;
    private double price;
    private String status;

    public Order(int contractNumber, int productId, int customerId, int quantity, double price) {
        this.contractNumber = contractNumber;
        this.productId = productId;
        this.customerId = customerId;
        this.orderDate = new Date(System.currentTimeMillis());
        this.quantity = quantity;
        this.price = price;
        this.status = "opened";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(int contractNumber) {
        this.contractNumber = contractNumber;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("contract=").append(contractNumber);
        sb.append(", orderDate=").append(orderDate);
        sb.append(", customerId=").append(customerId);
        sb.append(", productId=").append(productId);
        sb.append(", quantity=").append(quantity);
        sb.append(", price=").append(price);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
