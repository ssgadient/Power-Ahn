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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.LocalDate;
import java.sql.*;

public class TaskDatabase {
    static final String DB_URL = "jdbc:mysql://localhost:3306";
    static final String USER = "root"; // Local server user
    static final String PASS = "sanh2001"; // Local server password

    // Created a branch called
    public static void main(String[] args) throws SQLException {
        // Database 'Task' already created:
        // createTable("Tasks");
        insertTask("Tasks", new Task("JoyceCA", LocalDateTime.of(2022, 10, 28, 23, 59, 59), "11111"));
        readTask();
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
     * Create a SQL table called Tasks
     * (includes taskName, dueDate, appID)
     * DATE - format YYYY-MM-DD
     **/
    public static void createTable() {
        // This URL redirects to 'Task' database
        String dataBase = "jdbc:mysql://localhost:3306/Task";

        try (Connection conn = DriverManager.getConnection(dataBase, USER, PASS);
                Statement stmt = conn.createStatement();) {
            // Set SQL command:
            String createTableCommand = "CREATE TABLE Tasks " +
                    "(taskName VARCHAR(20) not NULL, " +
                    " dueDate DATE, " +
                    " dueTime TIME, " +
                    " appID VARCHAR(20), " +
                    " PRIMARY KEY ( taskName ))";

            // Execute command
            stmt.executeUpdate(createTableCommand);
            // Success message
            System.out.println("Created table in database...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create & Insert tasks into our database table.
     * Paramters: String tableName, object of type Task
     */
    public static void insertTask(String tableName, Task task) {
        String taskName = task.taskName;
        LocalDateTime deadline = task.deadline;
        String appID = task.getAppID();

        // Locate the location of our 'Task' database where our 'Tasks' table resides.
        String dataBase = "jdbc:mysql://localhost:3306/Task";

        // Open a connection
        try (Connection conn = DriverManager.getConnection(dataBase, USER, PASS);
                Statement stmt = conn.createStatement();) {

            System.out.println("Inserting records into the table...");

            // Separate LocalDateTime Java object into two parts (date & time):
            LocalDate localDate = deadline.toLocalDate();
            LocalTime localTime = deadline.toLocalTime();

            // Reformat into SQL standard format:
            java.sql.Date dueDate = java.sql.Date.valueOf(localDate);
            java.sql.Time dueTime = java.sql.Time.valueOf(localTime);

            // Create insert query:
            String insertQuery = "INSERT INTO " + tableName + " VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);

            // Populate query with values:
            pstmt.setString(1, taskName);
            pstmt.setDate(2, dueDate);
            pstmt.setObject(3, dueTime);
            pstmt.setString(4, appID);

            pstmt.execute();

            // Success message:
            System.out.println("Rows inserted ....");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads & Select tasks
     */
    public static void readTask() {
        String dataBase = "jdbc:mysql://localhost:3306/Task";
        // Open a connection
        try (Connection conn = DriverManager.getConnection(dataBase, USER, PASS);
                Statement stmt = conn.createStatement();) {

            // Set read Query:
            String readQuery = "SELECT taskName, dueTime, appID FROM Tasks";

            // Store query results into ResultSet
            ResultSet queryResults = stmt.executeQuery(readQuery);

            // Extract query data from result set
            while (queryResults.next()) {
                // Retrieve results by column name
                System.out.print("Task: " + queryResults.getString("taskName"));
                System.out.print(", Deadline: " + queryResults.getString("dueTime"));
                System.out.print(", App ID: " + queryResults.getString("appID"));
                // Add a empty line for readability:
                System.out.println("");

            }

            // Success message:
            System.out.println("Query results printed.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * DISCLAIMER: Needs tweaking to account for different number of parameters.
     * Update tasks
     * Parameters: String tableName
     */
    public static void updateTask() {
        String dataBase = "jdbc:mysql://localhost:3306/Task";
        // Open a connection
        try (Connection conn = DriverManager.getConnection(dataBase, USER, PASS);
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

    /**
     * Drop table
     */
    public static void dropTable(String tableName) {
        String dataBase = "jdbc:mysql://localhost:3306/Task";

        // Open a connection
        try (Connection conn = DriverManager.getConnection(dataBase, USER, PASS);
                Statement stmt = conn.createStatement();) {
            String dropQuery = "DROP TABLE " + tableName;
            stmt.executeUpdate(dropQuery);
            System.out.println("Table " + tableName + " has been deleted!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
