package dao.custom;

import dao.SuperDAO;
import entity.CustomEntity;

public interface QueryDAO extends SuperDAO {
    CustomEntity getCustomerDetail1(String orderId);
    CustomEntity getCustomerDetail2(String customerId);
    CustomEntity getOrderDetail(String orderId);

}
