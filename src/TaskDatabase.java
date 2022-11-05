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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.sql.*;

public class TaskDatabase {
    static final String DB_URL = "jdbc:mysql://localhost:3306";
    static final String USER = "root"; // Local server user
    static final String PASS = "sanh2001"; // Local server password
    static final String dataBase = "jdbc:mysql://localhost:3306/Task"; // This URL redirects to 'Task' database
    static final SimpleDateFormat formatTime = new SimpleDateFormat("hh:mm:ss aa");

    // Created a branch called
    public static void main(String[] args) throws SQLException {
        // Database 'Task' already created:
        // createDatabase("Task");
        // createTable();
        // dropTable("Tasks");
        // deleteTask("Database JDBC Task");
        insertTask("Tasks", new Task("Database JDBC Task", LocalDateTime.of(2001, 5, 1, 18, 0, 0),
                LocalDateTime.of(2022, 11, 5, 14, 00, 00), "Unity"));
        // readTask("taskName, startDate, end, duration, appID");
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
        try (Connection conn = DriverManager.getConnection(dataBase, USER, PASS);
                Statement stmt = conn.createStatement();) {
            // Set SQL command:
            String createTableCommand = "CREATE TABLE Tasks " +
                    "(taskName VARCHAR(20) not NULL, " +
                    " startDate DATE, " +
                    " startTime VARCHAR(20), " +
                    " endDate DATE, " +
                    " endTime VARCHAR(20), " +
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

            // Turn time object into a String to insert into Database
            String startTime_toString = formatTime.format(task_startTime);
            String endTime_toString = formatTime.format(task_endTime);
            // String endTime_toString =
            // endTime.format(DateTimeFormatter.ofPattern(timeFormatter));

            // Populate query with values:
            pstmt.setString(1, taskName);
            pstmt.setDate(2, task_startDate);
            pstmt.setString(3, startTime_toString);
            pstmt.setDate(4, task_endDate);
            pstmt.setString(5, endTime_toString);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Delete tasks with command: DELETE FROM {table} where {condition}
     * Parameter: Name of task (taskName) to delete
     */
    public static void deleteTask(String taskName) {
        try (Connection conn = DriverManager.getConnection(dataBase, USER, PASS);
                Statement stmt = conn.createStatement();) {

            // Delete query:
            String deleteQuery = "DELETE FROM Tasks WHERE taskName = ? ";

            PreparedStatement deleteStatement = conn.prepareStatement(deleteQuery);
            // Populate query with values:
            deleteStatement.setString(1, taskName);

            // Execute command:
            deleteStatement.execute();

            System.out.println("Task " + taskName + " has been deleted!");
            /*
             * // View changes:
             * while (queryResults.next()) {
             * // Retrieve by column name
             * System.out.print("Task: " + queryResults.getString("taskName"));
             * System.out.print("startDate: " + queryResults.getString("startDate"));
             * System.out.print("startTime: " + queryResults.getString("startTime"));
             * System.out.print("endDate: " + queryResults.getString("endDate"));
             * System.out.print("endTime: " + queryResults.getString("endTime"));
             * System.out.print("duration: " + queryResults.getString("taskName"));
             * System.out.print("App ID: " + queryResults.getString("appID"));
             */
            // Close query:
            // queryResults.close();

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
