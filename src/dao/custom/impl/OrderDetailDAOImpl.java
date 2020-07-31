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


    public List<OrderDetail> findAll()throws Exception{
            ResultSet rst = CrudUtil.execute("SELECT * FROM orderDetail");
            ArrayList<OrderDetail> orderDetails = new ArrayList<>();
            while (rst.next()){
                orderDetails.add( new OrderDetail(rst.getString(1), rst.getString(2),
                        rst.getInt(3),rst.getBigDecimal(4)));
            }
            return orderDetails;
    }

    @Override
    public OrderDetail find(OrderDetailPK key)throws Exception {
            ResultSet rst = CrudUtil.execute("SELECT * FROM orderDetail WHERE orderId =? AND itemCode =?",key.getOrderId(),key.getItemCode());
            if (rst.next()){
                return new OrderDetail(rst.getString(1), rst.getString(2),
                        rst.getInt(3),rst.getBigDecimal(4));
            }
        return null;
    }

    @Override
    public boolean save(OrderDetail OrderDetail) throws Exception{
            return CrudUtil.execute("INSERT INTO orderDetail VALUES (?,?,?,?)",
                    OrderDetail.getOrderDetailPK().getOrderId(),
                    OrderDetail.getOrderDetailPK().getItemCode(),OrderDetail.getOrderQty(),
                    OrderDetail.getUnitprice());
    }

    @Override
    public boolean update(OrderDetail OrderDetail)throws Exception {
            return CrudUtil.execute("UPDATE orderDetail SET orderQty=?,unitPrice=? WHERE orderId =?,itemCode=?",
                    OrderDetail.getOrderQty(),OrderDetail.getUnitprice(),
                    OrderDetail.getOrderDetailPK().getOrderId(),OrderDetail.getOrderDetailPK().getItemCode());
    }

    @Override
    public boolean delete(OrderDetailPK orderDetailPK)throws Exception {
            return CrudUtil.execute("DELETE FROM orderDetail WHERE orderId =?, itemCode=?",
                    orderDetailPK.getOrderId(),orderDetailPK.getItemCode());
    }
}
