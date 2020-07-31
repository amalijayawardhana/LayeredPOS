package business;

import dao.DAOFactory;
import dao.DAOType;
import dao.custom.ItemDAO;
import entity.Item;
import util.ItemTM;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ItemBO {
    public static List<ItemTM> getAllItems(){
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

    public static String getNewitemCode(){
        ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOType.ITEM);
        String lastitemCode = null;
        try {
            lastitemCode = itemDAO.getLastitemCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOType.ITEM);
        try {
            return itemDAO.save(new Item(code, description, BigDecimal.valueOf(unitPrice), qtyOnHand));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteItem(String itemCode){
        ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOType.ITEM);
        try {
            return itemDAO.delete(itemCode);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateItem(String description, int qtyOnHand, double unitPrice, String itemCode){
        ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOType.ITEM);
        try {
            return itemDAO.update(new Item(itemCode, description, BigDecimal.valueOf(unitPrice),qtyOnHand));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
