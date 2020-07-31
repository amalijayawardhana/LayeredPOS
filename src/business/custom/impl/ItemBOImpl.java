package business.custom.impl;

import business.custom.ItemBO;
import dao.DAOFactory;
import dao.DAOType;
import dao.custom.ItemDAO;
import entity.Item;
import util.ItemTM;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBO {
    public List<ItemTM> getAllItems() throws Exception{
        ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOType.ITEM);
        List<Item> allItems = null;
        try {
            allItems = itemDAO.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<ItemTM> items = new ArrayList<>();
        for (Object i : allItems) {
            Item item = (Item) i;
            items.add(new ItemTM(item.getItemcode(),item.getDescription(),item.getqtyOnHand(),item.getUnitprice().doubleValue()));
        }
        return items;
    }

    public String getNewitemCode()throws Exception{
        ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOType.ITEM);
        String lastitemCode = null;
            lastitemCode = itemDAO.getLastitemCode();
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

    public boolean saveItem(String code, String description, int qtyOnHand, double unitPrice)throws Exception{
        ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOType.ITEM);
            return itemDAO.save(new Item(code, description, BigDecimal.valueOf(unitPrice), qtyOnHand));
    }

    public boolean deleteItem(String itemCode) throws Exception{
        ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOType.ITEM);
            return itemDAO.delete(itemCode);
    }

    public boolean updateItem(String description, int qtyOnHand, double unitPrice, String itemCode)throws Exception{
        ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOType.ITEM);
            return itemDAO.update(new Item(itemCode, description, BigDecimal.valueOf(unitPrice),qtyOnHand));
    }
}
