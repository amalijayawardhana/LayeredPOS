package business;

import dao.*;
import db.DBConnection;
import entity.Customer;
import entity.Item;
import entity.Order;
import entity.OrderDetail;
import util.CustomerTM;
import util.ItemTM;
import util.OrderDetailTM;
import util.OrderTM;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BusinessLogic {
    public static String getNewCustomerId(){
        String lastCustomerId = CustomerDAO.getLastCustomerId();
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
        List<Customer> allCustomers = CustomerDAO.findAllCustomers();
        ArrayList<CustomerTM> customers = new ArrayList<>();
        for (Customer customer : allCustomers) {
            customers.add(new CustomerTM(customer.getId(),customer.getName(),customer.getAddress()));
        }
        return customers;
    }

    public static boolean saveCustomer(String id, String name, String address){
        return CustomerDAO.saveCustomer(new Customer(id,name,address));
    }

    public static boolean deleteCustomer(String customerId){
        return CustomerDAO.deleteCustomer(customerId);
    }


    public static boolean updateCustomer(String name, String address, String customerId){
        return CustomerDAO.updateCustomer(new Customer(customerId, name, address));
    }

    public static List<ItemTM> getAllItems(){
        List<Item> allItems = ItemDAO.findAllItems();
        ArrayList<ItemTM> items = new ArrayList<>();
        for (Item item : allItems) {
            items.add(new ItemTM(item.getItemcode(),item.getDescription(),item.getqtyOnHand(),item.getUnitprice().doubleValue()));
        }
        return items;
    }

    public static String getNewitemCode(){
        String lastitemCode = ItemDAO.getLastitemCode();
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
        return ItemDAO.saveItem(new Item(code, description, BigDecimal.valueOf(unitPrice), qtyOnHand));
    }

    public static boolean deleteItem(String itemCode){
        return ItemDAO.deleteItem(itemCode);
    }

    public static boolean updateItem(String description, int qtyOnHand, double unitPrice, String itemCode){
        return ItemDAO.updateItem(new Item(itemCode, description, BigDecimal.valueOf(unitPrice),qtyOnHand));
    }

    public static String getNewOrderId(){
        String lastOrderId = OrderDAO.getLastOrderId();
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


/*    public static boolean placeOrder(OrderTM order, List<OrderDetailTM> orderDetails){
        return DataLayer.placeOrder(order, orderDetails);
    }*/

/*    public static boolean placeOrder(OrderTM order, List<OrderDetailTM> orderDetails){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            boolean resultSaveOrder = DataLayer.saveOrder(order);
            if (!resultSaveOrder){
                connection.rollback();
                return false;
            }
                boolean resultSaveOrderDetail = DataLayer.saveOrderDetail(order, orderDetails);
                if (!resultSaveOrderDetail){
                    connection.rollback();
                    return false;
                }
                boolean resultUpdateQty = DataLayer.updateItemQty(orderDetails);
                if (!resultUpdateQty){
                    connection.rollback();
                    return false;
                }

            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return false;
        }finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }*/

/*    public static boolean placeOrder(OrderTM order, List<OrderDetailTM> orderDetails){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            boolean result = OrderDAO.saveOrder(new Order(order.getOrderId(), Date.valueOf(order.getOrderDate()),order.getCustomerId()));
            if (!result){
                connection.rollback();
                return false;
            }
            //to iterate order detail one by one
            for (OrderDetailTM orderDetail : orderDetails) {
                //save order detail to entity (from OrderTM)
                result = OrderDetailDAO.saveOrderDetail(new OrderDetail(order.getOrderId(), orderDetail.getCode(), orderDetail.getQty(),
                        BigDecimal.valueOf(orderDetail.getUnitPrice())));

                if (!result) {
                    connection.rollback();
                    return false;
                }
                //find item which related with order detail
                Item item = ItemDAO.findItem(orderDetail.getCode());
                //reduce the sold qty from existing item qty
                item.setQtyonhand(item.getqtyOnHand() - orderDetail.getQty());
                //update qty on hand
                result = ItemDAO.updateItem(item);
                if (!result) {
                    connection.rollback();
                    return false;
                }
            }
            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (Throwable throwables) {
                throwables.printStackTrace();
            }
            return false;
        }finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }*/
    public static boolean placeOrders(OrderTM order, List<OrderDetailTM> orderDetails) {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            boolean result = OrderDAO.saveOrder(new Order(order.getOrderId(), Date.valueOf(order.getOrderDate()), order.getCustomerId()));
            if (!result) {
                connection.rollback();
                return false;
            }
            for (OrderDetailTM orderDetail : orderDetails) {
                result = OrderDetailDAO.saveOrderDetail(new OrderDetail(order.getOrderId(), orderDetail.getCode(), orderDetail.getQty(), BigDecimal.valueOf(orderDetail.getUnitPrice())));

                if (!result) {
                    connection.rollback();
                    return false;
                }
                Item item = ItemDAO.findItem(orderDetail.getCode());
                item.setQtyOnHand(item.getqtyOnHand()-orderDetail.getQty());
                System.out.println(item.getqtyOnHand()-orderDetail.getQty());
                result = ItemDAO.updateItem(item);
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
