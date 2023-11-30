package dk.easv.bll;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String JDBC_URL = "jdbc:sqlserver://10.176.111.34\\EASV-DB4:1433;databaseName=mytunes_groupg;trustServerCertificate=true";
    private static final String USER = "CSe2023b_e_6";
    private static final String PASSWORD = "CSe2023bE6#23";

    public static void main(String[] args) {
        try {
            testDatabaseConnection();
            System.out.println("Database connection successful!");
        } catch (SQLException e) {
            System.err.println("Error connecting to the database:");
            e.printStackTrace();
        }
    }

    private static void testDatabaseConnection() throws SQLException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
        }
    }
}