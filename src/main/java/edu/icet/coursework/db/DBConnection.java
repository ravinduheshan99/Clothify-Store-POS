package edu.icet.coursework.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection instance;
    private Connection connection;

    //Make constructor private
    private DBConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clothify_store","root","Rv@mysql123");
    }

    public Connection getConnection(){
        return connection;
    }

    //Singleton
    public  static  DBConnection getInstance() throws ClassNotFoundException, SQLException {
        if (instance==null){
            instance = new DBConnection();
            return instance;
        }
        return instance;
    }

}
