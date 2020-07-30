package dao;

import entity.Order;

import java.util.List;

public interface OrderDAO extends SuperDAO {
    public String getLastOrderId();
/*    public List<Order> findAllOrders();
    public Order findOrder(String orderId);
    public boolean saveOrder(Order order);
    public boolean updateOrder(Order order);
    public boolean deleteOrder(String orderId);*/

    }
