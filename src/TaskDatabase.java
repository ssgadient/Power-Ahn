// Connect Java with MySQL driver
/**
 * Notes: 
 * INSERT INTO {table} (attribute a, attribute b) VALUES (a, b)
 * UPDATE {table} set attribute a = {value} where {condition}
 * DELETE FROM {table} where {condition}
 */

// Java Database Connectivity (JDBC)
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;

public class TaskDatabase {
    static final String DB_URL = "jdbc:mysql://localhost:3306";
    static final String USER = "root"; // Local server user
    static final String PASS = "sanh2001"; // Local server password

    // public Database {
    // }

    // Created a branch called
    public static void main(String[] args) throws SQLException {
        // Open a connection
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();) {
            // Set SQL command(s)
            String sql = "INSERT INTO Registration VALUES (100, 'Zara', 'Ali', 18)";

            // ResultSet rs = stmt.executeUpdate(sql);
            System.out.println("Database created successfully...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Create 'Task' database
    public void databaseCreation() {
        // Open a connection
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();) {
            // Set SQL command here:
            String sql = "CREATE DATABASE Task";
            // Execute SQL command here:
            stmt.executeUpdate(sql);
            // Print success message here:
            System.out.println("Database created successfully...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create table 'Tasks' (includes taskName, dueDate, appID)
     * DATE - format YYYY-MM-DD
     **/
    public void createTable() {
        // Open connection
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();) {
            // Set SQL command:
            String createTableCommand = "CREATE TABLE Tasks " +
                    "(taskName VARCHAR(20) not NULL, " +
                    " dueDate DATE, " +
                    " appID VARCHAR(20), " +
                    " PRIMARY KEY ( taskName ))";

            // Execute command
            stmt.executeUpdate(createTableCommand);
            // Success message
            System.out.println("Created table in given database...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method creates & inserts tasks
     */
    public void insertTask() {
        // Open a connection
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();) {
            // Create Insert Query (taskName, dueDate, appID)
            System.out.println("Inserting records into the table...");
            String insertCommand = "INSERT INTO Task VALUES ('HUM Essay', 2001-05-01, '12346')";

            // Execute command
            stmt.executeUpdate(insertCommand);

            // Success message:
            System.out.println("Inserted records into the table...");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method reads/selects tasks
     */
    public void readTask() {
        // Open a connection
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();) {

            // Set read Query:
            String readQuery = "SELECT taskName, dueTime, appID FROM Tasks";

            // Store query results into ResultSet
            ResultSet queryResults = stmt.executeQuery(readQuery);

            // Extract query data from result set
            while (queryResults.next()) {
                // Retrieve by column name
                System.out.print("ID: " + queryResults.getString("taskName"));
                System.out.print(", Age: " + queryResults.getString("dueTime"));
                System.out.print(", First: " + queryResults.getString("appID"));
            }

            // Success message:
            System.out.println("Query results printed: ");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method updates/changes tasks
     */
    public void updateTask() {

    }

    /**
     * Method delete tasks
     */
    public void deleteTask() {

    }
}
