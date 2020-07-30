package entity;

import java.io.Serializable;
import java.sql.Date;

public class Order implements SuperEntity {
    private String orderId;
    private Date orderDate;
    private String customerId;

    public Order() {
    }

    public Order(String orderId, Date orderDate, String customerId) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerId = customerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", orderdate=" + orderDate +
                ", customerId='" + customerId + '\'' +
                '}';
    }
}
