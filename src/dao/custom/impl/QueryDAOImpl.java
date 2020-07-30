package dao.custom.impl;

import dao.custom.QueryDAO;
import db.DBConnection;
import entity.CustomEntity;
import entity.SuperEntity;

import java.io.Serializable;
import java.sql.*;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public CustomEntity getCustomerDetail1(String orderId) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT o.OrderID,c.CustomerName, c.customerId \n" +
                    "FROM customer c INNER JOIN `order` o on c.CustomerID = o.CustomerID\n" +
                    " WHERE o.OrderID =?");
            pstm.setObject(1,orderId);
            ResultSet rst = pstm.executeQuery();
            if (rst.next()){
                return new CustomEntity(rst.getString(1),rst.getString(2),rst.getString(3));
            }
            return null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public CustomEntity getCustomerDetail2(String customerId) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT o.OrderID,c.CustomerName, c.customerId \n" +
                    "FROM customer c INNER JOIN `order` o on c.CustomerID = o.CustomerID\n" +
                    " WHERE o.OrderID =?");
            pstm.setObject(1,customerId);
            ResultSet rst = pstm.executeQuery();
            if (rst.next()){
                return new CustomEntity(rst.getString(1),rst.getString(2),rst.getString(3));
            }
            return null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public CustomEntity getOrderDetail(String orderId) {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement
                    ("SELECT o.orderId, o.orderDate, c.customerId, c.customerName, SUM(od.orderQty *od.unitprice) AS total\n" +
                    "FROM `order` o INNER JOIN customer c ON o.CustomerID = c.CustomerID\n" +
                    "                INNER JOIN orderdetail od on o.OrderID = od.OrderID WHERE o.OrderID=?");
            pstm.setObject(1,orderId);

            ResultSet rst = pstm.executeQuery();
            if (rst.next()){
                return new CustomEntity(rst.getString(1),
                        rst.getDate(2),rst.getString(3)
                ,rst.getString(4),rst.getDouble(5));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

}
