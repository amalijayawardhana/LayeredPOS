package dao.impl;

import db.DBConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import util.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataLayer {

    public static String getLastCustomerId(){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Customer ORDER BY customerid DESC  LIMIT 1");
            if (rst.next()){
                return rst.getString(1);
            }else{
                return null;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public static List<CustomerTM> getAllCustomers(){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Customer");
            ArrayList<CustomerTM> customers = new ArrayList<>();
            while (rst.next()){
                customers.add(new CustomerTM(rst.getString(1),
                        rst.getString(2),
                        rst.getString(3)));
            }
            return customers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean  saveCustomers(CustomerTM customer){

        PreparedStatement pstm = null;
        try {
            pstm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO Customer VALUES (?,?,?)");
            pstm.setObject(1, customer.getId());
            pstm.setObject(2, customer.getName());
            pstm.setObject(3, customer.getAddress());
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean  updateCustomer(CustomerTM customer){
        PreparedStatement pstm = null;

        try {
            pstm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE Customer SET customername=?, customeraddress=? WHERE customerid=?");
            pstm.setObject(1, customer.getName());
            pstm.setObject(2, customer.getAddress());
            pstm.setObject(3, customer.getId());
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean deleteCustomer(String customerId){
        PreparedStatement pstm = null;
        try {
            pstm = DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM Customer WHERE customerid=?");
            pstm.setObject(1, customerId);
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
    //item
    public static String getLastitemCode(){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Item ORDER BY itemcode DESC  LIMIT 1");
            if (rst.next()){
                return rst.getString(1);
            }else{
                return null;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public static List<ItemTM> getAllItems(){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = null;
            stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Item");
            ArrayList<ItemTM> items = new ArrayList<>();
                while (rst.next()) {
                    items.add(new ItemTM(rst.getString(1),
                            rst.getString(2),
                            rst.getInt(4),
                            rst.getDouble(3)));
                }
                return items;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean  saveItem(ItemTM item){

        PreparedStatement pstm = null;
        try {
            pstm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO Item VALUES (?,?,?,?)");
            pstm.setObject(1, item.getCode());
            pstm.setObject(2, item.getDescription());
            pstm.setObject(4, item.getQtyOnHand());
            pstm.setObject(3, item.getUnitPrice());
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }


    }

    public static boolean updateItem(ItemTM item){
        PreparedStatement pstm = null;

        try {
            pstm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE itemcode=?");
            pstm.setObject(1, item.getDescription());
            pstm.setObject(2, item.getQtyOnHand());
            pstm.setObject(4, item.getUnitPrice());
            pstm.setObject(3, item.getCode());
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean deleteItem(String itemCode){
        PreparedStatement pstm = null;
        try {
            pstm = DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM Item WHERE itemcode=?");
            pstm.setObject(1, itemCode);
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    //PlaceOrder

/*    public static boolean placeOrder(OrderTM order, List<OrderDetailTM> orderDetails){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO Orders VALUES (?,?,?)");
            pstm.setObject(1, order.getOrderId());
            pstm.setObject(2, order.getOrderDate());
            pstm.setObject(3, order.getCustomerId());
            int affectedRows = pstm.executeUpdate();
            if (affectedRows ==0){
                connection.rollback();
                return false;
            }

            for (OrderDetailTM orderDetail : orderDetails) {
                pstm = connection.prepareStatement("INSERT INTO OrderDetail VALUES (?,?,?,?)");
                pstm.setObject(1, order.getOrderId());
                pstm.setObject(2, orderDetail.getCode());
                pstm.setObject(3, orderDetail.getQty());
                pstm.setObject(4, orderDetail.getUnitPrice());
                affectedRows = pstm.executeUpdate();
                if (affectedRows == 0) {
                    connection.rollback();
                    return false;
                }

                pstm = connection.prepareStatement("UPDATE Item SET qtyOnHand=qtyOnHand - ? WHERE itemcode=?");
                pstm.setObject(1, orderDetail.getQty());
                affectedRows = pstm.executeUpdate();

                if (affectedRows == 0) {
                    connection.rollback();
                    return false;
                }
            }
            connection.commit();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return false;
        }finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }*/


    public static boolean saveOrder(OrderTM order){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO Orders VALUES (?,?,?)");
            pstm.setObject(1, order.getOrderId());
            pstm.setObject(2, order.getOrderDate());
            pstm.setObject(3, order.getCustomerId());
            return pstm.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean saveOrderDetail(OrderTM order, List<OrderDetailTM> orderDetails){
        Connection connection = DBConnection.getInstance().getConnection();

                PreparedStatement pstm = null;
                try {
                    pstm = connection.prepareStatement("INSERT INTO OrderDetail VALUES (?,?,?,?)");
                    boolean result = false;
                    for (OrderDetailTM orderDetail : orderDetails) {
                        pstm.setObject(1, order.getOrderId());
                        pstm.setObject(2, orderDetail.getCode());
                        pstm.setObject(3, orderDetail.getQty());
                        pstm.setObject(4, orderDetail.getUnitPrice());
                        result = pstm.executeUpdate()>0;
                        if (!result) {
                            return false;
                        }
                    }
                    return true;
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    return false;
                }
    }

    public static boolean updateItemQty(List<OrderDetailTM> orderDetails){
        Connection connection = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = null;
        try {
            pstm = connection.prepareStatement("UPDATE Item SET qtyOnHand=qtyOnHand - ? WHERE itemcode=?");
            boolean result = false;
            for (OrderDetailTM orderDetail: orderDetails) {
                pstm.setObject(1, orderDetail.getQty());
                pstm.setObject(2, orderDetail.getCode());
                result= pstm.executeUpdate()>0;

                if (!result) {
                    return false;
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

//    public static boolean updateQty(List<OrderDetail>)

    public static String getLastOrderId(){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM orders ORDER BY orderId DESC  LIMIT 1");
            if (rst.next()){
                return rst.getString(1);
            }else{
                return null;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}
