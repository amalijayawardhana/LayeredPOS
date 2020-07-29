package dao.impl;

import dao.CustomerDAO;
import db.DBConnection;
import entity.Customer;
import entity.Item;
import util.CustomerTM;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    public String getLastCustomerId(){
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

    public List<Customer> findAllCustomers(){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Customer");
            ArrayList<Customer> customers = new ArrayList<>();
            while (rst.next()){
                customers.add( new Customer(rst.getString(1), rst.getString(2),
                        rst.getString(3)));
            }
            return customers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Customer findCustomer(String id){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Customer WHERE id =?");
            pstm.setObject(1,id);
            ResultSet rst = pstm.executeQuery();
            if (rst.next()){
                return new Customer(rst.getString(1), rst.getString(2),
                        rst.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean saveCustomer(Customer customer){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO Customer VALUES (?,?,?)");
            pstm.setObject(1,customer.getId());
            pstm.setObject(2,customer.getName());
            pstm.setObject(3,customer.getAddress());
            return pstm.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateCustomer(Customer customer){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("UPDATE Customer SET customerName=?, CustomerAddress=? WHERE customerId=?");
            pstm.setObject(3, customer.getId());
            pstm.setObject(1, customer.getName());
            pstm.setObject(2, customer.getAddress());
            return pstm.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
    public boolean deleteCustomer(String id){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM Customer WHERE customerId =?");
            pstm.setObject(1,id);
            return pstm.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
