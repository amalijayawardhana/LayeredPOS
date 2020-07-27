package dao;

import db.DBConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import util.CustomerTM;

import java.sql.*;
import java.util.ArrayList;

public class DataLayer {

    public static CustomerTM getAllCustomer(){
        Connection connection = DBConnection.getInstance().getConnection();
        Statement stm = null;
        try {
            stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * Customer");
            ArrayList<CustomerTM> customers = new ArrayList<>();
            while (rst.next()) {
                String id = rst.getString(1);
                String name = rst.getString(2);
                String address = rst.getString(3);
                customers.add(new CustomerTM(id, name, address));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public static void saveCustomers(CustomerTM customer){
        PreparedStatement pstm = null;
        CustomerTM
        try {
            pstm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO Customer VALUES (?,?,?)");
            pstm.setObject(1, customer.getId());
            pstm.setObject(2, customer.getName());
            pstm.setObject(3, customer.getAddress());
            int affectedRows = pstm.executeUpdate();
            if (affectedRows == 0) {
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public static void updateCustomers(CustomerTM customer){
        PreparedStatement pstm = null;

        try {
            pstm = DBConnection.getInstance().getConnection().prepareStatement("UPDATE Customer SET customername=?, customeraddress=? WHERE customerid=?");
            pstm.setObject(1, customer.setName(););
            pstm.setObject(2, customer.se());
            pstm.setObject(3, customer.getId());
            if (pstm.executeUpdate() == 0) {
                new Alert(Alert.AlertType.ERROR, "Failed to update the customer", ButtonType.OK).show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void deleteCustomers(){

    }
}
