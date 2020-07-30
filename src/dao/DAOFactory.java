package dao;

import dao.custom.CustomerDAO;
import dao.custom.ItemDAO;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailDAO;
import dao.custom.impl.CustomerDAOImpl;
import dao.custom.impl.ItemDAOImpl;
import dao.custom.impl.OrderDAOImpl;
import dao.custom.impl.OrderDetailDAOImpl;
import db.DBConnection;

public class DAOFactory {
    private static DAOFactory daoFactory;

    public DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    /*    public CustomerDAO getCustomerDAO(){
            return new CustomerDAOImpl();
        }
        public ItemDAO getItemDAO(){
            return new ItemDAOImpl();
        }
        public OrderDAO getOrderDAO(){
            return new OrderDAOImpl();
        }
        public OrderDetailDAO getOrderDetailDAO(){
            return new OrderDetailDAOImpl();
        }*/
    public SuperDAO getDAO(int daoType) {
        switch (daoType) {
            case 0:
                return new CustomerDAOImpl();
            case 1:
                return new ItemDAOImpl();
            case 2:
                return new OrderDAOImpl();
            case 3:
                return new OrderDetailDAOImpl();
            default:
                return null;
        }
    }
}
