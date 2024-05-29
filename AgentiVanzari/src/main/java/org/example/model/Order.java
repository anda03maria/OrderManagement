package org.example.model;

import jakarta.persistence.*;


@jakarta.persistence.Entity
@Table(name = "Orders")
public class Order implements Entity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "agent_id")
    private String agentId;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "client_email")
    private String clientEmail;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "courier")
    private String courierType;

    public Order() {}

    public Order(Integer integer, String agentId, Integer productId, Integer quantity, String clientName, String clientEmail, String paymentMethod, String courierType) {
        this.id = integer;
        this.agentId = agentId;
        this.productId = productId;
        this.quantity = quantity;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.paymentMethod = paymentMethod;
        this.courierType = courierType;
    }

    public Order(String agentId, Integer productId, Integer quantity, String clientName, String clientEmail, String paymentMethod, String courierType) {
        this.agentId = agentId;
        this.productId = productId;
        this.quantity = quantity;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.paymentMethod = paymentMethod;
        this.courierType = courierType;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCourierType() {
        return courierType;
    }

    public void setCourierType(String courierType) {
        this.courierType = courierType;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer integer) {
        this.id = integer;
    }
}
