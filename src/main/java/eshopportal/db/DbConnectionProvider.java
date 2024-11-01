package eshopportal.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectionProvider {

    private static  DbConnectionProvider instance;

    private final String JDBC_URL = "jdbc:mysql://localhost:3306/eshop2";
    private final String JDBC_USERNAME ="root";
    private final String JDBC_PASSWORD = "root";

    private Connection connection;

    private DbConnectionProvider(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public  static DbConnectionProvider getInstance(){
        if (instance == null){
            instance = new DbConnectionProvider();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    }

