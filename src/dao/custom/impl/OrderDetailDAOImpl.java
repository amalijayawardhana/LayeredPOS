package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDetailDAO;
import db.DBConnection;
import entity.OrderDetail;
import entity.OrderDetailPK;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {


    public List<OrderDetail> findAll(){
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM orderDetail");
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

    @Override
    public OrderDetail find(OrderDetailPK key) {
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM orderDetail WHERE orderId =? AND itemCode =?",key.getOrderId(),key.getItemCode());
            if (rst.next()){
                return new OrderDetail(rst.getString(1), rst.getString(2),
                        rst.getInt(3),rst.getBigDecimal(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean save(OrderDetail OrderDetail) {
        try {
            return CrudUtil.execute("INSERT INTO orderDetail VALUES (?,?,?,?)",
                    OrderDetail.getOrderDetailPK().getOrderId(),
                    OrderDetail.getOrderDetailPK().getItemCode(),OrderDetail.getOrderQty(),
                    OrderDetail.getUnitprice());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(OrderDetail OrderDetail) {
        try {
            return CrudUtil.execute("UPDATE orderDetail SET orderQty=?,unitPrice=? WHERE orderId =?,itemCode=?",
                    OrderDetail.getOrderQty(),OrderDetail.getUnitprice(),
                    OrderDetail.getOrderDetailPK().getOrderId(),OrderDetail.getOrderDetailPK().getItemCode());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(OrderDetailPK orderDetailPK) {
        try {
            return CrudUtil.execute("DELETE FROM orderDetail WHERE orderId =?, itemCode=?",
                    orderDetailPK.getOrderId(),orderDetailPK.getItemCode());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
