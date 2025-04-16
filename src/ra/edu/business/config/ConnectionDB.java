package ra.edu.business.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB{
    private final static String URL = "jdbc:mysql://localhost:3306/StudentAndCourseManagement";
    private final static String USER = "root";
    private final static String PASSWORD = "a@1234";

//    public static Connection openConnection(){
//        Connection con = null;
//        try{
//            con = DriverManager.getConnection(URL, USER, PASSWORD);
//
//        }catch(SQLException e){
////            System.out.println("");
//        }
//    }
}
