package business;

import dao.DataLayer;
import db.DBConnection;
import util.CustomerTM;
import util.ItemTM;
import util.OrderDetailTM;
import util.OrderTM;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class BusinessLogic {
    public static String getNewCustomerId(){
        String lastCustomerId = DataLayer.getLastCustomerId();
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

    public static List<CustomerTM> getAllCustomers(){
        return DataLayer.getAllCustomers();
    }
    public static boolean saveCustomer(String id, String name, String address){
        return DataLayer.saveCustomers(new CustomerTM(id,name,address));
    }

    public static boolean deleteCustomer(String customerId){
        return DataLayer.deleteCustomer(customerId);
    }

    public static boolean updateCustomer(String name, String address, String customerId){
        return DataLayer.updateCustomer(new CustomerTM(customerId, name, address));
    }

    public static List<ItemTM> getAllItems(){
        return DataLayer.getAllItems();
    }

    public static String getNewitemCode(){
        String lastitemCode = DataLayer.getLastitemCode();
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
        return DataLayer.saveItem(new ItemTM(code, description, qtyOnHand, unitPrice));
    }

    public static boolean deleteItem(String itemCode){
        return DataLayer.deleteItem(itemCode);
    }

    public static boolean updateItem(String description, int qtyOnHand, double unitPrice, String itemCode){
        return DataLayer.updateItem(new ItemTM(itemCode, description, qtyOnHand, unitPrice));
    }

    public static String getNewOrderId(){
        String lastOrderId = DataLayer.getLastOrderId();
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

    public static boolean placeOrder(OrderTM order, List<OrderDetailTM> orderDetails){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            boolean resultSaveOrder = DataLayer.saveOrder(order);
            if (!resultSaveOrder){
                connection.rollback();
                return false;
            }
            for (OrderDetailTM orderDetail : orderDetails) {
                boolean resultSaveOrderDetail = DataLayer.saveOrderDetail(order, orderDetail);
                if (!resultSaveOrderDetail){
                    connection.rollback();
                    return false;
                }
                boolean resultUpdateQty = DataLayer.updateItemQty(orderDetail);
                if (!resultUpdateQty){
                    connection.rollback();
                    return false;
                }
            }
            connection.commit();
            return false;
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

    }


}
