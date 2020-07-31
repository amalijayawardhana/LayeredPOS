package dao;

import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CrudUtil {
    public static boolean executeUpdate(String sql){
        return false;
    }
    public static ResultSet executeQuery(String sql, Object... parameters){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            int i = 0;
            System.out.println(parameters[0]);
            for (Object parameter : parameters) {
                i++;
                pstm.setObject(i,parameter);
            }
            return pstm.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
