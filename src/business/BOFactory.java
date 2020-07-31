package business;

import business.custom.impl.CustomerBOImpl;
import business.custom.impl.ItemBOImpl;
import business.custom.impl.OrderBOImpl;
import dao.DAOFactory;
import dao.DAOType;
import dao.SuperDAO;
import dao.custom.impl.*;

public class BOFactory {
    private static BOFactory bofactory;

    public BOFactory() {
    }
    public static BOFactory getInstance(){
        return (bofactory == null) ? bofactory = new BOFactory() : bofactory;

    }

    public <T extends SuperBO> T getBO(BOType boType) {
        switch (boType) {
            case CUSTOMER:
                return (T) new CustomerBOImpl();
            case ITEM:
                return (T) new ItemBOImpl();
            case ORDER:
                return (T) new OrderBOImpl();
            default:
                return null;
        }
    }
}
