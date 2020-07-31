package business;

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
                return (T) new CustomerDAOImpl();
            case ITEM:
                return (T) new ItemDAOImpl();
            case ORDER:
                return (T) new OrderDAOImpl();
            default:
                return null;
        }
    }
}
