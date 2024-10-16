package database;


import java.sql.Connection;
import java.sql.DriverManager;

public class databaseConnection {
    public static Connection getConnection(){
        Connection databaselink = null;
        String databaseName = "quanlythuvien";
        String databaseUser = "root";
        String databasePassword = "dung@151825";
        String url = "jdbc:mysql://localhost:3306/" + databaseName;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaselink = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch(Exception e){
                e.printStackTrace();
        }

        return databaselink;
    }
}
