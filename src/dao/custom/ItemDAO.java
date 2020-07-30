package dao.custom;

import dao.SuperDAO;
import entity.Item;

public interface ItemDAO extends SuperDAO<Item,String> {
    public String getLastitemCode();
/*    public List<Item> findAllItems();
    public Item findItem(String id);
    public boolean saveItem(Item item);
    public boolean updateItem(Item item);
    public boolean deleteItem(String id);*/
}
