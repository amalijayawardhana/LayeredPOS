package dao.custom.impl;

import dao.DAOFactory;
import dao.DAOType;
import dao.SuperDAO;
import dao.custom.QueryDAO;
import entity.CustomEntity;

public class QueryDAOTest {
    public static void main(String[] args) {
        QueryDAO queryDAO = DAOFactory.getInstance().getDAO(DAOType.QUERY);
        //getCustomerDetail1
        CustomEntity d001 = null;
        try {
            d001 = queryDAO.getCustomerDetail1("D001");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(d001.getCustomerId()+", "+d001.getCustomerName()+", "
        +d001.getOrderId());
        //getOrderDetail
        CustomEntity d002 = null;
        try {
            d002 = queryDAO.getOrderDetail("D002");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(d002.getOrderId()+", "+d002.getOrderDate()+", "+d002.getCustomerId()+", "+
                d002.getCustomerName()+", "+d002.getTotal());
        //getCustomerDetail2
        CustomEntity c001 = null;
        try {
            c001 = queryDAO.getCustomerDetail2("C001");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(c001.getOrderId()+", "+c001.getCustomerName()+", "
                +c001.getCustomerId());

    }
}
