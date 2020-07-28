package entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class Item implements Serializable {
    private String itemCode;
    private String description;
    private BigDecimal unitprice;
    private int qtyOnHand;

    public Item() {
    }

    public Item(String itemcode, String description, BigDecimal unitprice, int qtyOnHand) {
        this.itemCode = itemcode;
        this.description = description;
        this.unitprice = unitprice;
        this.qtyOnHand = qtyOnHand;
    }

    public String getItemcode() {
        return itemCode;
    }

    public void setItemcode(String itemcode) {
        this.itemCode = itemcode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(BigDecimal unitprice) {
        this.unitprice = unitprice;
    }

    public int getqtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemcode='" + itemCode + '\'' +
                ", description='" + description + '\'' +
                ", unitprice=" + unitprice +
                ", qtyonhand=" + qtyOnHand +
                '}';
    }
}
