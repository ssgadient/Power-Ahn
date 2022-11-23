/**
 * Notes on SQL syntax: 
 * SELECT {attribute} FROM {table} WHERE {condition}
 * INSERT INTO {table} (column a, column b) VALUES (a, b)
 * UPDATE {table} SET {attribute} = {value} WHERE {condition}
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
import java.util.HashMap;
import java.time.format.DateTimeFormatter;
import java.sql.*;

public class TaskDatabase {
    static final String DB_URL = "jdbc:mysql://localhost:3306"; // My local host 
    static final String USER = "root"; // My local server username
    static final String PASS = "sanh2001"; // My local server password
    static final String dataBase = "jdbc:mysql://localhost:3306/Task"; // My local URL that redirects to the 'Task' database (if exists)
    static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // Reformatting date

    /**
     * The main() method is where I call and run my separate CRUD functions implemented below.
     **/
    public static void main(String[] args) throws SQLException {
        // createDatabase("Task");
        // createTable();
        // dropTable("Tasks");
        // deleteTask("Database JDBC Task");
        // readTask("taskName, startDate, endDate, duration, appID");
        /* insertTask("Tasks", new Task("Test1", LocalDateTime.of(2022, 12, 20, 10, 0), LocalDateTime.of(2022, 12, 29, 20, 0), "Unity"));
        insertTask("Tasks", new Task("Test2", LocalDateTime.of(2022, 12, 21, 10, 0), LocalDateTime.of(2022, 12, 29, 20, 0), "Unity"));
        insertTask("Tasks", new Task("Test3", LocalDateTime.of(2022, 12, 22, 10, 0), LocalDateTime.of(2022, 12, 29, 20, 0), "Unity"));
        insertTask("Tasks", new Task("Test4", LocalDateTime.of(2022, 12, 23, 10, 0), LocalDateTime.of(2022, 12, 29, 20, 0), "Unity"));
        insertTask("Tasks", new Task("Test5", LocalDateTime.of(2022, 12, 24, 10, 0), LocalDateTime.of(2022, 12, 29, 20, 0), "Unity"));
        insertTask("Tasks", new Task("Test6", LocalDateTime.of(2022, 12, 25, 10, 0), LocalDateTime.of(2022, 12, 29, 20, 0), "Unity")); */
        readClosestDate(2);
    }

    /**
     * Creates a new database with the name given by @param database
     * A database is the parent container. It can hold many tables.
     */
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
     * Creates a new table called 'Tasks' consisting of columns:
     * [taskName, startDate, endDate, duration (in seconds), appID]
     * The table's primary key is the task name.
     **/
    public static void createTable() {
        try (Connection conn = DriverManager.getConnection(dataBase, USER, PASS);
                Statement stmt = conn.createStatement();) {

            // Populate table with columns:
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
     * Dynamically calculate estimatedDuration.
     */
    public static void insertTask(String tableName, Task task) {
        String taskName = task.getTaskName();
        LocalDateTime taskStart = task.getStartTime(); // split into separate date & time
        LocalDateTime taskEnd = task.getEndTime(); // split into separate date & time
        String appID = task.getAppID();

        // Calculate estimate duration in seconds
        task.setEstimatedDuration(task.calculateEstimatedDuration(taskStart, taskEnd));
        int taskEstimatedDuration = (int) task.getEstimatedDuration().toSeconds(); // typecast getDuration as an integer to store

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
     * Return the n closest tasks whose dates are closest to the current time.
     * Returns an ArrayList containing the taskName(s).
     * 
     * Query:
     * SELECT taskName
     * FROM Tasks
     * WHERE startDate > NOW()
     * ORDER BY startTime
     * LIMIT N
     */
    public static HashMap<String, ArrayList<Object>> readClosestDate(int n) {
        // Create a HashMap storing (String ArrayList<String>)
        HashMap<String, ArrayList<Object>> tasks = new HashMap<>();

        try (Connection conn = DriverManager.getConnection(dataBase, USER, PASS);
                Statement stmt = conn.createStatement();) {

            // Query to select startDates from Tasks table
            String readQuery = "SELECT * FROM Tasks WHERE startDate > NOW() ORDER BY startDate LIMIT " + n;

            // Store query results into ResultSet
            ResultSet queryResults = stmt.executeQuery(readQuery);

            System.out.println("Here are the " + n + " closest tasks:");
            // Extract query data from result set
            while (queryResults.next()) {
                // Retrieve results by column names and create new variables for them:
                String taskName = queryResults.getString("taskName");

                // Convert startDate String into a LocalDate object with formatter and parse() method
                String startDate_string = queryResults.getString("startDate");
                LocalDateTime startDate = LocalDateTime.parse(startDate_string, formatter);

                // Convert endDate String into a LocalDate object with formatter and parse() method
                String endDate_string = queryResults.getString("endDate");
                LocalDateTime endDate = LocalDateTime.parse(endDate_string, formatter);

                String appID = queryResults.getString("appID");

                System.out.println(taskName);
                System.out.println(startDate);
                System.out.println(endDate);
                System.out.println(appID); 
                System.out.println(""); 

                // Create and return a new ArrayList that stores taskNames
                ArrayList<Object> taskInfo = new ArrayList<>();

                // Populate ArrayList taskInfo with information about the task:
                taskInfo.add(startDate);
                taskInfo.add(endDate);
                taskInfo.add(appID); 

                // Then, populate HashMap with each (task name, task info array):
                tasks.put(taskName, taskInfo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Iterate through all the key (task names)
        System.out.println("Here are the keys of the hashmap: ");
        for (String taskName : tasks.keySet()) {
            System.out.println("Task: " + taskName);
        }

        System.out.println();
        
        // Iterate through all the values (ArrayList of task info)
        System.out.println("Here are the values of the hashmap: ");
        for (ArrayList<Object> arraylist : tasks.values()) {
            System.out.println("ArrayList: " + arraylist);
        }
        
        
        System.out.println();
        System.out.println("Here is your HashMap of tasks: ");
        System.out.println(tasks);
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
     * Deletes a task(s) wit name of @param taskName from table 'Tasks'
     */
    public static void deleteTask(String taskName) {
        try (Connection conn = DriverManager.getConnection(dataBase, USER, PASS);
                Statement stmt = conn.createStatement();) {

            // Delete query:
            String deleteQuery = "DELETE FROM Tasks WHERE taskName = ? ";

            // Prepare a statment to run deleteQuery
            PreparedStatement deleteStatement = conn.prepareStatement(deleteQuery);

            // Populate query with values:
            deleteStatement.setString(1, taskName);

            // Execute query:
            deleteStatement.execute();

            // Success message
            System.out.println("Task " + taskName + " has been deleted!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Drops database with name @param database
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
     * Drops table with name @param tableName
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
