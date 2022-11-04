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
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.sql.*;

public class TaskDatabase {
    static final String DB_URL = "jdbc:mysql://localhost:3306";
    static final String USER = "root"; // Local server user
    static final String PASS = "sanh2001"; // Local server password
    static final String timeFormatter = "hh:mm:ss a"; // Format our time objects into AM and PM formats

    // Created a branch called
    public static void main(String[] args) throws SQLException {
        // Database 'Task' already created:
        // createDatabase("Task");
        // Need to create a new table
        // createTable();
        // dropTable("Tasks");
        // insertTask("Tasks", LocalDateTime.of(2022, 10, 1, 10,0, 1),
        // LocalDateTime.of(2022, 11, 1, 10,20, 0), new Task("PA",
        // calculateEstimatedDuration(LocalDateTime startDateTime, LocalDateTime
        // endDateTime)));
        // insertTask("Tasks", new Task("Discussion", LocalDateTime.of(2020, 10, 1, 10,
        // 0, 1),
        // LocalDateTime.of(2024, 1, 25, 10, 20, 0), "Unity"));
        readTask("taskName, startDate, endDate, duration, appID");
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
     * Display duration in terms of hours, minutes, seconds
     **/
    public static void createTable() {
        // This URL redirects to 'Task' database
        String dataBase = "jdbc:mysql://localhost:3306/Task";

        try (Connection conn = DriverManager.getConnection(dataBase, USER, PASS);
                Statement stmt = conn.createStatement();) {
            // Set SQL command:
            String createTableCommand = "CREATE TABLE Tasks " +
                    "(taskName VARCHAR(20) not NULL, " +
                    " startDate DATE, " +
                    " startTime TIME, " +
                    " endDate DATE, " +
                    " endTime TIME, " +
                    " duration INT, " +
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
     * Insert tasks into our database table.
     * Paramters: String tableName, object of type Task (taskName, startTime,
     * endTime, appID).
     * Dynamically calculate and set estimatedDuration.
     * 
     * For now, me needs to be reformmated before to 24-hour format before being
     * inserted
     * startTime.format(DateTimeFormatter.ofPattern(timeFormatter))
     */
    public static void insertTask(String tableName, Task task) {
        String taskName = task.getTaskName();
        LocalDateTime taskStart = task.getStartTime(); // split into separate date & time
        LocalDateTime taskEnd = task.getEndTime(); // split into separate date & time
        String appID = task.getAppID();

        // After new Task object is created, we can calculate and assign the estimated
        // duration
        task.setEstimatedDuration(task.calculateEstimatedDuration(taskStart, taskEnd));
        int taskEstimatedDuration = (int) task.getEstimatedDuration().toHours(); // typecast getDuration as an integer

        // Locate the location of our 'Task' database where our 'Tasks' table resides.
        String dataBase = "jdbc:mysql://localhost:3306/Task";

        // Open a connection
        try (Connection conn = DriverManager.getConnection(dataBase, USER, PASS);
                Statement stmt = conn.createStatement();) {

            System.out.println("Inserting records into the table...");

            // Separate each LocalDateTime Java object into two parts (date & time):
            LocalDate startDate = taskStart.toLocalDate();
            LocalTime startTime = taskStart.toLocalTime();
            LocalDate endDate = taskEnd.toLocalDate();
            LocalTime endTime = taskEnd.toLocalTime();

            // Reformat into SQL standard format:
            java.sql.Date task_startDate = java.sql.Date.valueOf(startDate);
            java.sql.Time task_startTime = java.sql.Time.valueOf(startTime); // Reformat time into AM/PM
            java.sql.Date task_endDate = java.sql.Date.valueOf(endDate);
            java.sql.Time task_endTime = java.sql.Time.valueOf(endTime); // Reformat time into AM/PM

            // Create insert query:
            String insertQuery = "INSERT INTO " + tableName + " VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);

            // Populate query with values:
            pstmt.setString(1, taskName);
            pstmt.setDate(2, task_startDate);
            pstmt.setTime(3, task_startTime);
            pstmt.setDate(4, task_endDate);
            pstmt.setTime(5, task_endTime);
            pstmt.setInt(6, taskEstimatedDuration);
            pstmt.setString(7, appID);

            pstmt.execute();

            // Success message:
            System.out.println("Rows inserted ....");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads & Select tasks
     * Parameters: A String selectColumns. Example: "taskName, task_startDate,
     * task_endDate, taskEstimatedDuration, appID"
     */
    public static void readTask(String selectColumns) {
        String dataBase = "jdbc:mysql://localhost:3306/Task";
        // Open a connection
        try (Connection conn = DriverManager.getConnection(dataBase, USER, PASS);
                Statement stmt = conn.createStatement();) {

            // Set read Query:
            // String readQuery = "SELECT taskName, dueTime, appID FROM Tasks";
            String readQuery = "SELECT " + selectColumns + " FROM Tasks";

            // Store query results into ResultSet
            ResultSet queryResults = stmt.executeQuery(readQuery);

            // Extract query data from result set
            while (queryResults.next()) {
                // Retrieve results by column name
                System.out.print("Task: " + queryResults.getString("taskName"));
                System.out.print(", startDate: " + queryResults.getString("startDate"));
                System.out.print(", endDate: " + queryResults.getString("endDate"));
                System.out.print(", taskEstimatedDuration: " + queryResults.getString("duration"));
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
     * Update tasks with command: UPDATE {table} set {attribute} = {value} where
     * {condition} = {value}
     * Parameters: String tableName, String columnName, String newValue, String
     * condition, String conditionValue
     */
    public static void updateTask(String tableName, String attribute, String newValue, String condition,
            String conditionValue) {
        String dataBase = "jdbc:mysql://localhost:3306/Task";
        // Open a connection
        try (Connection conn = DriverManager.getConnection(dataBase, USER, PASS);
                Statement stmt = conn.createStatement();) {

            // Update command:
            String updateQuery = "UPDATE " + tableName + " SET " + attribute + "=?" + " WHERE " + condition
                    + "=?";
            PreparedStatement pstmt = conn.prepareStatement(updateQuery);
            // Populate query with values:
            pstmt.setString(1, newValue);
            pstmt.setString(2, conditionValue);

            // Execute command:
            pstmt.execute();

            // Success message:
            System.out.println("Table updated ....");

            // Execute command:
            // stmt.executeUpdate(updateCommand);

            // String SQL = "update Student set age = ? where id = ?";

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Delete tasks with command: DELETE FROM {table} where {condition}
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
            System.out.println("Database " + database + " was successfully dropped successfully...");
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
