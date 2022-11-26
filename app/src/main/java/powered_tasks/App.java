package powered_tasks;


public class App {
    public static void main(String[] args) {
        TaskDatabase.createTable();

        MainBackgroundThread mbt = new MainBackgroundThread();
        mbt.start();

        MainFrame.openApp(args);

        mbt.doStop();
        TaskDatabase.dropTable("Tasks");
        TaskDatabase.shutdownDatabase();
        System.out.println("App Closed.");
    }
}
