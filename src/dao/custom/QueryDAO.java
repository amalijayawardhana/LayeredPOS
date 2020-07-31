package dao.custom;

import dao.SuperDAO;
import entity.CustomEntity;

public interface QueryDAO extends SuperDAO {
    CustomEntity getCustomerDetail1(String orderId)throws Exception;
    CustomEntity getCustomerDetail2(String customerId)throws Exception;
    CustomEntity getOrderDetail(String orderId)throws Exception;

}
