package dao.custom.impl;

import dao.DAOFactory;
import dao.DAOType;

public class QueryDAOTest {
    public static void main(String[] args) {
        DAOFactory.getInstance().getDAO(DAOType.QUERY);

    }
}
