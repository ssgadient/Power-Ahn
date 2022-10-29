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

    // Created a branch called
    public static void main(String[] args) throws SQLException {
        // Create SQL database Task:
        // createDatabase("Task");
        createTable("Tasks");
        // dropDatabase("Task");

    }

    // Create 'Task' database
    public static void createDatabase(String database) {
        // Open a connection
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();) {
            // Set SQL command here:
            String sql = "CREATE DATABASE " + database;
            // Execute SQL command here:
            stmt.executeUpdate(sql);
            // Print success message here:
            System.out.println("Database " + database + " created successfully...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create 'Tasks' table
     * (includes taskName, dueDate, appID)
     * DATE - format YYYY-MM-DD
     **/
    public static void createTable(String tableName) {
        // This URL redirects to 'Task' database
        String dataBase = "jdbc:mysql://localhost:3306/Task";

        try (Connection conn = DriverManager.getConnection(dataBase, USER, PASS);
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
            System.out.println("Created " + tableName + " in database...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create & Insert tasks
     * Takes Task object as argument
     * public Task(String taskName, LocalDateTime dueTime, String appID) {
        // Reformate dueTime to YYYY-MM-DD format
        DateTimeFormatter reformateDate = DateTimeFormatter.ofPattern("YYYY-MM-dd");
        dueTime.format(reformateDate);

        task.taskName;
        task.dueTime = dueTime;
        task.appID = appID;
    }
     */
    public static void insertTask(Task task) {
        String name = task.taskName;

        // Open a connection
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();) {
            // Create Insert Query (taskName, dueDate, appID)
            System.out.println("Inserting records into the table...");
            String insertCommand = "INSERT INTO Task VALUES ('task', 2001-05-01, '12346')";

            // Execute command
            stmt.executeUpdate(insertCommand);

            // Success message:
            System.out.println("Inserted records into the table...");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads & Select tasks
     */
    public static void readTask() {
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
                System.out.print("Task: " + queryResults.getString("taskName"));
                System.out.print(", Deadline: " + queryResults.getString("dueTime"));
                System.out.print(", App ID: " + queryResults.getString("appID"));
            }

            // Success message:
            System.out.println("Query results printed: ");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * DISCLAIMER: Needs tweaking to account for different number of parameters.
     * Update tasks
     */
    public static void updateTask() {
        // Open a connection
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();) {
            // Update command:
            String updateCommand = "UPDATE Tasks " +
                    "SET age = 30 WHERE id in (100, 101)";

            // Execute command:
            stmt.executeUpdate(updateCommand);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Delete tasks
     * Parameter: Name of task (taskName) to delete
     */
    public static void deleteTask(String taskName) {
        // Open a connection
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();) {

            // Delete command:
            String deleteCommand = "DELETE FROM Tasks " +
                    "WHERE taskName = " + taskName;
            // Execute command:
            stmt.executeUpdate(deleteCommand);
            ResultSet queryResults = stmt.executeQuery(deleteCommand);

            // View changes:
            while (queryResults.next()) {
                // Retrieve by column name
                System.out.print("Task: " + queryResults.getString("taskName"));
                System.out.print(", Deadline: " + queryResults.getString("dueTime"));
                System.out.print(", App ID: " + queryResults.getString("appID"));
            }

            // Close query:
            queryResults.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Drop database
     */
    public static void dropDatabase(String database) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();) {
            String sql = "DROP DATABASE " + database;
            stmt.executeUpdate(sql);
            System.out.println("Database 'Task' dropped successfully...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
