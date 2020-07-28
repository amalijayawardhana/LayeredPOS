package dao;

import dao.CustomerDAO;
import entity.Customer;

import java.util.List;

public class CustomerDAOTest {
    public static void main(String[] args) {
        //test find and save customer
/*        boolean result = CustomerDAO.saveCustomer(new Customer("C035", "Appu", "Galle"));
        System.out.println(result);
        List<Customer> allCustomers = CustomerDAO.findAllCustomers();
        for (Customer allCustomer : allCustomers) {
            System.out.println(allCustomer);
        }*/
        assert CustomerDAO.findAllCustomers().size() == 35;
    }
}
