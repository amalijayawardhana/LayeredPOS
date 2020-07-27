package business;

import dao.DataLayer;
import util.CustomerTM;

import java.util.List;

public class BusinessLogic {
    public static List<CustomerTM> getAllCustomers(){
        return DataLayer.getAllCustomers();
    }
    public static boolean saveCustomer(String id, String name, String address){
        return DataLayer.saveCustomers(new CustomerTM(id,name,address));
    }
}
