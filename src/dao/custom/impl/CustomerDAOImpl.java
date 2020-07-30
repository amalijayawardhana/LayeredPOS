package dao.custom.impl;

import dao.custom.CustomerDAO;
import db.DBConnection;
import entity.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public Customer find(String key) {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Customer WHERE id =?");
            pstm.setObject(1,key);
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

    @Override
    public boolean save(Customer customer) {
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

    @Override
    public boolean update(Customer customer) {
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

    @Override
    public boolean delete(String key) {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM Customer WHERE customerId =?");
            pstm.setObject(1,key);
            return pstm.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

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

    @Override
    public List<Customer> findAll(){
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
}


