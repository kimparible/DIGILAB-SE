package db; // Simpan di package util

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/digital_library?useSSL=false";
    private static final String JDBC_USERNAME = "root"; // Ganti dengan username MySQL kamu
    private static final String JDBC_PASSWORD = "";     // Ganti dengan password MySQL kamu

    public static Connection getConnection() {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
        System.err.println("Database Error: " + e.getMessage());
        return null;
    }
}

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception dengan lebih baik di aplikasi riil
        }
    }
}