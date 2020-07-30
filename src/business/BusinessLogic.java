package business;


import dao.impl.CustomerDAOImpl;

import dao.impl.ItemDAOImpl;

import dao.impl.OrderDAOImpl;

import dao.impl.OrderDetailDAOImpl;
import db.DBConnection;
import entity.Customer;
import entity.Item;
import entity.Order;
import entity.OrderDetail;
import util.CustomerTM;
import util.ItemTM;
import util.OrderDetailTM;
import util.OrderTM;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BusinessLogic {
    public static String getNewCustomerId(){
        CustomerDAOImpl customerDAO = new CustomerDAOImpl();
        String lastCustomerId = customerDAO.getLastCustomerId();
        if (lastCustomerId == null){
            return "C001";
        }else{
            int maxId=  Integer.parseInt(lastCustomerId.replace("C",""));
            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "C00" + maxId;
            } else if (maxId < 100) {
                id = "C0" + maxId;
            } else {
                id = "C" + maxId;
            }
            return id;
        }
    }

/*    public static List<CustomerTM> getAllCustomers(){
        return DataLayer.getAllCustomers();
    }*/
/*    public static boolean saveCustomer(String id, String name, String address){
        return DataLayer.saveCustomers(new CustomerTM(id,name,address));
    }*/
 /*   public static boolean deleteCustomer(String customerId){
        return DataLayer.deleteCustomer(customerId);
    }*/
 /*    public static boolean updateCustomer(String name, String address, String customerId){
        return DataLayer.updateCustomer(new CustomerTM(customerId, name, address));
    }*/

    public static List<CustomerTM> getAllCustomers(){
        CustomerDAOImpl customerDAO = new CustomerDAOImpl();
        List<Object> allCustomers = customerDAO.findAll();
        ArrayList<CustomerTM> customers = new ArrayList<>();
        for (Object c : allCustomers) {
            Customer customer = (Customer) c;
            customers.add(new CustomerTM(customer.getId(),customer.getName(),customer.getAddress()));
        }
        return customers;
    }

    public static boolean saveCustomer(String id, String name, String address){
        CustomerDAOImpl customerDAO = new CustomerDAOImpl();
        return customerDAO.save(new Customer(id,name,address));
    }

    public static boolean deleteCustomer(String customerId){

        CustomerDAOImpl customerDAO = new CustomerDAOImpl();
        return customerDAO.delete(customerId);
    }


    public static boolean updateCustomer(String name, String address, String customerId){
        CustomerDAOImpl customerDAO = new CustomerDAOImpl();
        return customerDAO.update(new Customer(customerId, name, address));
    }

    public static List<ItemTM> getAllItems(){
        ItemDAOImpl itemDAO = new ItemDAOImpl();
        List<Object> allItems = itemDAO.findAll();
        ArrayList<ItemTM> items = new ArrayList<>();
        for (Object i : allItems) {
            Item item = (Item) i;
            items.add(new ItemTM(item.getItemcode(),item.getDescription(),item.getqtyOnHand(),item.getUnitprice().doubleValue()));
        }
        return items;
    }

    public static String getNewitemCode(){
        ItemDAOImpl itemDAO = new ItemDAOImpl();
        String lastitemCode = itemDAO.getLastitemCode();
        if (lastitemCode == null){
            return "I001";
        }else{
            int maxId=  Integer.parseInt(lastitemCode.replace("I",""));
            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "I00" + maxId;
            } else if (maxId < 100) {
                id = "I0" + maxId;
            } else {
                id = "I" + maxId;
            }
            return id;
        }
    }

    public static boolean saveItem(String code, String description, int qtyOnHand, double unitPrice){
        ItemDAOImpl itemDAO = new ItemDAOImpl();
        return itemDAO.save(new Item(code, description, BigDecimal.valueOf(unitPrice), qtyOnHand));
    }

    public static boolean deleteItem(String itemCode){
        ItemDAOImpl itemDAO = new ItemDAOImpl();
        return itemDAO.delete(itemCode);
    }

    public static boolean updateItem(String description, int qtyOnHand, double unitPrice, String itemCode){
        ItemDAOImpl itemDAO = new ItemDAOImpl();
        return itemDAO.update(new Item(itemCode, description, BigDecimal.valueOf(unitPrice),qtyOnHand));
    }

    public static String getNewOrderId(){
        OrderDAOImpl orderDAO = new OrderDAOImpl();
        String lastOrderId = orderDAO.getLastOrderId();
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
        OrderDAOImpl orderDAO = new OrderDAOImpl();
        ItemDAOImpl itemDAO = new ItemDAOImpl();
        OrderDetailDAOImpl orderDetailDAO = new OrderDetailDAOImpl();
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
                Item item = (Item) itemDAO.find(orderDetail.getCode());
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
