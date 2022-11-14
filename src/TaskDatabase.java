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
import java.util.ArrayList;
// import java.time.LocalTime;
// import java.time.LocalDate;
import java.sql.*;

public class TaskDatabase {
    static final String DB_URL = "jdbc:mysql://localhost:3306";
    static final String USER = "root"; // Local server user
    static final String PASS = "sanh2001"; // Local server password
    static final String dataBase = "jdbc:mysql://localhost:3306/Task"; // This URL redirects to 'Task' database
    static final SimpleDateFormat formatTime = new SimpleDateFormat("hh:mm:ss aa");

    public static void main(String[] args) throws SQLException {
        // Database 'Task' already created:
        // createDatabase("Task");
        // createTable();
        // dropTable("Tasks");
        // deleteTask("Database JDBC Task");
        /*
         * insertTask("Tasks", new Task("Test0", LocalDateTime.of(2022, 11, 13, 10, 0),
         * LocalDateTime.of(2022, 11, 20, 20, 0), "Unity"));
         * insertTask("Tasks", new Task("Test1", LocalDateTime.of(2022, 11, 14, 10, 0),
         * LocalDateTime.of(2022, 11, 20, 20, 0), "Unity"));
         * insertTask("Tasks", new Task("Test2", LocalDateTime.of(2022, 11, 15, 10, 0),
         * LocalDateTime.of(2022, 11, 20, 20, 0), "Unity"));
         * insertTask("Tasks", new Task("Test3", LocalDateTime.of(2022, 11, 16, 10, 0),
         * LocalDateTime.of(2022, 11, 20, 20, 0), "Unity"));
         * insertTask("Tasks", new Task("Test4", LocalDateTime.of(2022, 11, 17, 10, 0),
         * LocalDateTime.of(2022, 11, 20, 20, 0), "Unity"));
         */
        // readTask("taskName, startDate, endDate, duration, appID");
        readClosestDate(2);
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
                    " startDate DATETIME, " +
                    " endDate DATETIME, " +
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
            /*
             * LocalDate startDate = taskStart.toLocalDate();
             * LocalTime startTime = taskStart.toLocalTime();
             * LocalDate endDate = taskEnd.toLocalDate();
             * LocalTime endTime = taskEnd.toLocalTime();
             */

            // Reformat into SQL standard format:
            java.sql.Timestamp task_startDate = java.sql.Timestamp.valueOf(task.getStartTime());
            // java.sql.Time task_startTime = java.sql.Time.valueOf(startTime);
            java.sql.Timestamp task_endDate = java.sql.Timestamp.valueOf(task.getEndTime());
            // java.sql.Time task_endTime = java.sql.Time.valueOf(endTime);

            // Create insert query:
            String insertQuery = "INSERT INTO " + tableName + " VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);

            // Turn time object into a String to insert into Database
            // String startTime_toString = formatTime.format(task_startTime); // Reformat
            // time into AM/PM
            // String endTime_toString = formatTime.format(task_endTime);
            // String endTime_toString =
            // endTime.format(DateTimeFormatter.ofPattern(timeFormatter));

            // Populate query with values:
            pstmt.setString(1, taskName);
            pstmt.setObject(2, task_startDate);
            // pstmt.setTime(3, task_startTime);
            pstmt.setObject(3, task_endDate);
            // pstmt.setTime(5, task_endTime);
            pstmt.setInt(4, taskEstimatedDuration);
            pstmt.setString(5, appID);
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
                // System.out.print(", startDate: " + queryResults.getString("startDate"));
                // System.out.print(", endDate: " + queryResults.getString("endDate"));
                // System.out.print(", taskEstimatedDuration: " +
                // queryResults.getString("duration"));
                // System.out.print(", App ID: " + queryResults.getString("appID"));

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
     * Return the n closest tasks whose dates are closest to the current time. Returns an ArrayList containing the taskName(s).
     * 
     * Query:
     * SELECT taskName
     * FROM Tasks
     * WHERE startDate > NOW()
     * ORDER BY startTime
     * LIMIT N
     */
    public static ArrayList<String> readClosestDate(int n) {
        // Create and return a new ArrayList that stores taskNames
        ArrayList<String> tasks = new ArrayList<String>();

        try (Connection conn = DriverManager.getConnection(dataBase, USER, PASS);
                Statement stmt = conn.createStatement();) {

            // Query to select startDates from Tasks table
            String readQuery = "SELECT taskName FROM Tasks WHERE startDate > NOW() ORDER BY startDate LIMIT " + n;

            // Store query results into ResultSet
            ResultSet queryResults = stmt.executeQuery(readQuery);

            // Extract query data from result set
            while (queryResults.next()) {
                // Retrieve results by column name
                System.out.print("Task: " + queryResults.getString("taskName"));
                System.out.println("");

                // Insert each taskName into the ArrayList
                tasks.add(queryResults.getString("taskName"));
            }
            // Success messages:
            System.out.println("Query results printed.");
            System.out.println("");
            System.out.println(tasks);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;

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
