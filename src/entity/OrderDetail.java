package entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderDetail implements Serializable {
    private OrderDetailPK orderDetailPK;
    private int orderQty;
    private BigDecimal unitprice;

    public OrderDetail() {
    }

    public OrderDetail(OrderDetailPK orderDetailPK, int orderQty, BigDecimal unitprice) {
        this.orderDetailPK = orderDetailPK;
        this.orderQty = orderQty;
        this.unitprice = unitprice;
    }

    public OrderDetail(String orderId, String itemCode, int orderQty, BigDecimal unitprice) {
        this.orderDetailPK = new OrderDetailPK(orderId,itemCode);
        this.orderQty = orderQty;
        this.unitprice = unitprice;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public BigDecimal getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(BigDecimal unitprice) {
        this.unitprice = unitprice;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderDetailPK=" + orderDetailPK +
                ", orderQty=" + orderQty +
                ", unitprice=" + unitprice +
                '}';
    }

    public OrderDetailPK getOrderDetailPK() {
        return orderDetailPK;
    }

    public void setOrderDetailPK(OrderDetailPK orderDetailPK) {
        this.orderDetailPK = orderDetailPK;
    }
}
