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
    public Item find(String key)throws Exception {
            ResultSet rst = CrudUtil.execute("SELECT * FROM Item WHERE itemCode =?",key);
            if (rst.next()) {
                return new Item(rst.getString(1), rst.getString(2),
                        rst.getBigDecimal(3), rst.getInt(4));
            }
        return null;
    }

    @Override
    public boolean save(Item item)throws Exception {
            return CrudUtil.execute("INSERT INTO Item VALUES (?,?,?,?)",item.getItemcode(),item.getDescription()
                    ,item.getUnitprice(),item.getqtyOnHand());
    }

    @Override
    public boolean update(Item item) throws Exception{
            return CrudUtil.execute("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE itemCode=?",item.getDescription()
                    ,item.getUnitprice(),item.getqtyOnHand(),item.getItemcode());
    }

    @Override
    public boolean delete(String key)throws Exception {
            return CrudUtil.execute("DELETE FROM Item WHERE itemCode =?", key);
    }

    @Override
    public List<Item> findAll()throws Exception {
            ResultSet rst = CrudUtil.execute("SELECT * FROM Item");
            ArrayList<Item> items = new ArrayList<>();
            while (rst.next()) {
                items.add(new Item(rst.getString(1), rst.getString(2),
                        rst.getBigDecimal(3), rst.getInt(4)));
            }
            return items;
    }

    public String getLastitemCode() throws Exception{
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Item ORDER BY itemcode DESC  LIMIT 1");
            if (rst.next()) {
                return rst.getString(1);
            } else {
                return null;
            }
    }
}
