package dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class CrudUtilTest {
    public static void main(String[] args) throws SQLException {
        //with an arrayList
//        List<String> parameters = new ArrayList<>();
//        parameters.add("C001");
//        parameters.add("Kasun");
//        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM customer WHERE customerId=? AND customerName =?",parameters);

        ResultSet rst = CrudUtil.<ResultSet>execute("SELECT * FROM customer WHERE customerId=? ","C001");
        System.out.println(rst.next());

        ResultSet rst2 = CrudUtil.execute("SELECT * FROM customer WHERE customerId=? ","C001");


    }
}