package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDAO;
import db.DBConnection;
import entity.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    public String getLastOrderId(){
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM `order` ORDER BY orderId DESC  LIMIT 1");
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

    public List<Order> findAll(){
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM `order`");
            ArrayList<Order> orders = new ArrayList<>();
            while (rst.next()){
                orders.add( new Order(rst.getString(1), rst.getDate(2),
                        rst.getString(3)));
            }
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Order find(String key) {
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM `order` WHERE orderId =?",key);
            if (rst.next()){
                return new Order(rst.getString(1), rst.getDate(2),
                        rst.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean save(Order order) {
        try {
            return CrudUtil.execute("INSERT INTO `order` VALUES (?,?,?)",order.getOrderId(),order.getOrderDate(),
                    order.getCustomerId());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Order order) {
        try {
            return CrudUtil.execute("UPDATE `order` SET orderDate=?,customerId=? WHERE orderId =?)"
                    ,order.getOrderDate(),order.getCustomerId(),order.getOrderId());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String key) {
        try {
            return CrudUtil.execute("DELETE FROM `order` WHERE orderId =?", key);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
