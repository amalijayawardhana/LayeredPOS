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


    public List<OrderDetail> findAllOrderDetails(){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM orderDetail");
            ArrayList<OrderDetail> orderDetails = new ArrayList<>();
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
    public OrderDetail findOrderDetail(OrderDetailPK orderDetailPK){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM orderDetail WHERE orderId =? AND itemCode =?");
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
    public boolean saveOrderDetail(OrderDetail OrderDetail){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO orderDetail VALUES (?,?,?,?)");
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
    public boolean updateOrderDetail(OrderDetail OrderDetail){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement
                    ("UPDATE orderDetail SET orderQty=?,unitPrice=? WHERE orderId =?,itemCode=?)");
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
    public boolean deleteOrderDetail(OrderDetailPK orderDetailPK){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM orderDetail WHERE orderId =?, itemCode=?");
            pstm.setObject(1,orderDetailPK.getOrderId());
            pstm.setObject(2,orderDetailPK.getItemCode());
            return pstm.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
