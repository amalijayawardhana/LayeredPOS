package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ItemDAO;
import db.DBConnection;
import entity.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public Item find(String key) {
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM Item WHERE itemCode =?",key);
            if (rst.next()) {
                return new Item(rst.getString(1), rst.getString(2),
                        rst.getBigDecimal(3), rst.getInt(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean save(Item item) {
        try {
            return CrudUtil.execute("INSERT INTO Item VALUES (?,?,?,?)",item.getItemcode(),item.getDescription()
                    ,item.getUnitprice(),item.getqtyOnHand());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Item item) {
        try {
            return CrudUtil.execute("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE itemCode=?",item.getDescription()
                    ,item.getUnitprice(),item.getqtyOnHand(),item.getItemcode());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String key) {
        try {
            return CrudUtil.execute("DELETE FROM Item WHERE itemCode =?", key);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public List<Item> findAll() {
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM Item");
            ArrayList<Item> items = new ArrayList<>();
            while (rst.next()) {
                items.add(new Item(rst.getString(1), rst.getString(2),
                        rst.getBigDecimal(3), rst.getInt(4)));
            }
            return items;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getLastitemCode() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Item ORDER BY itemcode DESC  LIMIT 1");
            if (rst.next()) {
                return rst.getString(1);
            } else {
                return null;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

}
