package dao.impl;

import dao.ItemDAO;
import db.DBConnection;
import entity.Customer;
import entity.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    public String getLastitemCode(){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Item ORDER BY itemcode DESC  LIMIT 1");
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

    public List<Item> findAllItems(){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Item");
            ArrayList<Item> items = new ArrayList<>();
            while (rst.next()){
                items.add( new Item(rst.getString(1), rst.getString(2),
                        rst.getBigDecimal(3),rst.getInt(4)));
            }
            return items;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Item findItem(String itemCode){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Item WHERE itemCode =?");
            pstm.setObject(1,itemCode);
            ResultSet rst = pstm.executeQuery();
            if (rst.next()){
                return new Item(rst.getString(1), rst.getString(2),
                        rst.getBigDecimal(3),rst.getInt(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean saveItem(Item item){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO Item VALUES (?,?,?,?)");
            pstm.setObject(1,item.getItemcode());
            pstm.setObject(2,item.getDescription());
            pstm.setObject(3,item.getUnitprice());
            pstm.setObject(4,item.getqtyOnHand());
            return pstm.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateItem(Item item){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE itemCode=?");
            pstm.setObject(4, item.getItemcode());
            pstm.setObject(1, item.getDescription());
            pstm.setObject(2, item.getUnitprice());
            pstm.setObject(3, item.getqtyOnHand());
            return pstm.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
    public boolean deleteItem(String itemCode){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM Item WHERE itemCode =?");
            pstm.setObject(1,itemCode);
            return pstm.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
