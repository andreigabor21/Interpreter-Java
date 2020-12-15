package GUI;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public Connection databaseLink;

    public Connection getConnection() {
        String url = "jdbc:sqlserver://DESKTOP-C0SCI39:1433;database=LoginJava;integratedSecurity=true";
        try {
            databaseLink = DriverManager.getConnection(url);
            System.out.println("Connected to MS SQL");
        } catch (SQLException throwables) {
            System.out.println("Error!");
            throwables.printStackTrace();
        }
        return databaseLink;
    }


}
