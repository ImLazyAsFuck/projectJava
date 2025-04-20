package ra.edu.business.config;

import ra.edu.utils.Print.PrintError;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB{
    private final static String URL = "jdbc:mysql://localhost:3306/project_java";
    private final static String USER = "root";
    private final static String PASSWORD = "a@1234";

    public static Connection openConnection(){
        Connection con = null;
        try{
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        }catch(SQLException e){
            PrintError.print(e.getMessage());
        }catch(Exception e){
            PrintError.print("Unknown error while opening connection");
        }
        return con;
    }

    public static void closeConnection(Connection con, CallableStatement cs){
        if(con != null){
            try{
                con.close();
            }catch(SQLException e){
                throw new RuntimeException(e);
            }
        }
        if(cs != null){
            try{
                cs.close();
            }catch(SQLException e){
                throw new RuntimeException(e);
            }
        }
    }
}
