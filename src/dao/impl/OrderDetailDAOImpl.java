package dao.impl;

import dao.OrderDetailDAO;
import db.DBConnection;
import entity.Customer;
import entity.Order;
import entity.OrderDetail;
import entity.OrderDetailPK;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {


    public List<Object> findAll(){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM orderDetail");
            ArrayList<Object> orderDetails = new ArrayList<>();
            while (rst.next()){
                orderDetails.add( new OrderDetail(rst.getString(1), rst.getString(2),
                        rst.getInt(3),rst.getBigDecimal(4)));
            }
            return orderDetails;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Object find(Object entity){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM orderDetail WHERE orderId =? AND itemCode =?");
            OrderDetailPK orderDetailPK = (OrderDetailPK) entity;
            pstm.setObject(1,orderDetailPK.getOrderId());
            pstm.setObject(2,orderDetailPK.getItemCode());

            ResultSet rst = pstm.executeQuery();
            if (rst.next()){
                return new OrderDetail(rst.getString(1), rst.getString(2),
                        rst.getInt(3),rst.getBigDecimal(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean save(Object entity){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO orderDetail VALUES (?,?,?,?)");
            OrderDetail OrderDetail = (OrderDetail) entity;
            pstm.setObject(1,OrderDetail.getOrderDetailPK().getOrderId());
            pstm.setObject(2,OrderDetail.getOrderDetailPK().getItemCode());
            pstm.setObject(3,OrderDetail.getOrderQty());
            pstm.setObject(4,OrderDetail.getUnitprice());

            return pstm.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean update(Object entity){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement
                    ("UPDATE orderDetail SET orderQty=?,unitPrice=? WHERE orderId =?,itemCode=?)");

            OrderDetail OrderDetail = (OrderDetail) entity;
            pstm.setObject(4,OrderDetail.getOrderDetailPK().getOrderId());
            pstm.setObject(3,OrderDetail.getOrderDetailPK().getItemCode());
            pstm.setObject(1,OrderDetail.getOrderQty());
            pstm.setObject(2,OrderDetail.getUnitprice());

            return pstm.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(Object entity){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM orderDetail WHERE orderId =?, itemCode=?");
            OrderDetailPK orderDetailPK = (OrderDetailPK) entity;
            pstm.setObject(1,orderDetailPK.getOrderId());
            pstm.setObject(2,orderDetailPK.getItemCode());
            return pstm.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
