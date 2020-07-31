package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.CustomerDAO;
import db.DBConnection;
import entity.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public Customer find(String key) throws Exception {
            ResultSet rst = CrudUtil.execute("SELECT * FROM Customer WHERE id =?",key);
            if (rst.next()){
                return new Customer(rst.getString(1), rst.getString(2),
                        rst.getString(3));
            }
        return null;
    }

    @Override
    public boolean save(Customer customer)throws Exception {
            return CrudUtil.execute("INSERT INTO Customer VALUES (?,?,?)",
                    customer.getId(),customer.getName(),customer.getAddress());
    }

    @Override
    public boolean update(Customer customer)throws Exception {

            return CrudUtil.execute("UPDATE Customer SET customerName=?, CustomerAddress=? WHERE customerId=?",
                    customer.getName(),customer.getAddress(),customer.getId());
    }

    @Override
    public boolean delete(String key) throws Exception{
            return CrudUtil.execute("DELETE FROM Customer WHERE customerId=?", key);
    }

    public String getLastCustomerId()throws Exception{
            ResultSet rst = CrudUtil.execute("SELECT * FROM Customer ORDER BY customerid DESC  LIMIT 1");
            if (rst.next()){
                return rst.getString(1);
            }else{
                return null;
            }
    }

    @Override
    public List<Customer> findAll()throws Exception{
            ResultSet rst = CrudUtil.execute("SELECT * FROM Customer");
            ArrayList<Customer> customers = new ArrayList<>();
            while (rst.next()){
                customers.add( new Customer(rst.getString(1), rst.getString(2),
                        rst.getString(3)));
            }
        return customers;
    }
}


