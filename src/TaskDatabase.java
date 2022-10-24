
// Look into JBDC 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TaskDatabase {
    static final String DB_URL = "jdbc:mysql://localhost:3306";
    static final String USER = "root"; // Local server user
    static final String PASS = "sanh2001"; // Local server password

    // public Database {
    // }

    public static void main(String[] args) throws SQLException {
        // Open a connection
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();) {
            String sql = "CREATE DATABASE STUDENTS"; // Database creation
            stmt.executeUpdate(sql);
            System.out.println("Database created successfully...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
