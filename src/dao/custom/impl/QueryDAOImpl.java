package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.QueryDAO;
import db.DBConnection;
import entity.CustomEntity;
import entity.SuperEntity;

import java.io.Serializable;
import java.sql.*;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public CustomEntity getCustomerDetail1(String orderId)throws Exception {
            ResultSet rst = CrudUtil.execute("SELECT o.OrderID,c.CustomerName, c.customerId \n" +
                    "FROM customer c INNER JOIN `order` o on c.CustomerID = o.CustomerID\n" +
                    " WHERE o.OrderID =?",orderId);
            if (rst.next()){
                return new CustomEntity(rst.getString(1),rst.getString(2),rst.getString(3));
            }
            return null;
    }

    @Override
    public CustomEntity getCustomerDetail2(String customerId)throws Exception {
            ResultSet rst = CrudUtil.execute("SELECT o.OrderID,c.CustomerName, c.customerId \n" +
                    "FROM customer c INNER JOIN `order` o on c.CustomerID = o.CustomerID\n" +
                    " WHERE o.customerId =?",customerId);
            if (rst.next()){
                return new CustomEntity(rst.getString(1),rst.getString(2),rst.getString(3));
            }
            return null;
    }

    @Override
    public CustomEntity getOrderDetail(String orderId)throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT o.orderId, o.orderDate, c.customerId, c.customerName, SUM(od.orderQty *od.unitprice) AS total\n" +
                "FROM `order` o INNER JOIN customer c ON o.CustomerID = c.CustomerID\n" +
                "                INNER JOIN orderdetail od on o.OrderID = od.OrderID WHERE o.OrderID=?", orderId);
        if (rst.next()) {
            return new CustomEntity(rst.getString(1),
                    rst.getDate(2), rst.getString(3)
                    , rst.getString(4), rst.getDouble(5));
        }
        return null;
    }
}
