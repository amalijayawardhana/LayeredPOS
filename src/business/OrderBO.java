package business;

import dao.DAOFactory;
import dao.DAOType;
import dao.custom.ItemDAO;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailDAO;
import db.DBConnection;
import entity.Item;
import entity.Order;
import entity.OrderDetail;
import util.OrderDetailTM;
import util.OrderTM;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class OrderBO {
    public static String getNewOrderId(){
        OrderDAO orderDAO = DAOFactory.getInstance().getDAO(DAOType.ORDER);
        String lastOrderId = null;
        try {
            lastOrderId = orderDAO.getLastOrderId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (lastOrderId == null){
            return "D001";
        }else{
            int maxId=  Integer.parseInt(lastOrderId.replace("D",""));
            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "D00" + maxId;
            } else if (maxId < 100) {
                id = "D0" + maxId;
            } else {
                id = "D" + maxId;
            }
            return id;
        }
    }
    public static boolean placeOrders(OrderTM order, List<OrderDetailTM> orderDetails) {
        Connection connection = DBConnection.getInstance().getConnection();
        OrderDAO orderDAO = DAOFactory.getInstance().getDAO(DAOType.ORDER);
        OrderDetailDAO orderDetailDAO = DAOFactory.getInstance().getDAO(DAOType.ORDERDETAIL);

        try {
            connection.setAutoCommit(false);

            boolean result = orderDAO.save(new Order(order.getOrderId(), Date.valueOf(order.getOrderDate()), order.getCustomerId()));
            if (!result) {
                connection.rollback();
                return false;
            }
            for (OrderDetailTM orderDetail : orderDetails) {
                result = orderDetailDAO.save(new OrderDetail(order.getOrderId(), orderDetail.getCode(), orderDetail.getQty(), BigDecimal.valueOf(orderDetail.getUnitPrice())));

                if (!result) {
                    connection.rollback();
                    return false;
                }
                ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOType.ITEM);
                Item item = itemDAO.find(orderDetail.getCode());
                item.setQtyOnHand(item.getqtyOnHand()-orderDetail.getQty());
                System.out.println(item.getqtyOnHand()-orderDetail.getQty());
                result = itemDAO.update(item);
                System.out.println(item);

                if (!result) {
                    connection.rollback();
                    return false;
                }

                connection.commit();
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }
}
