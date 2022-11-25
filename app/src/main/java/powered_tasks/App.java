package powered_tasks;


public class App {
    public static void main(String[] args) {
        //TaskDatabase.createDatabase("TaskDatabase");
        TaskDatabase.createTable();

        MainBackgroundThread mbt = new MainBackgroundThread();
        mbt.start();

        MainFrame.openApp(args);

        //mbt.doStop();
        TaskDatabase.shutdownDatabase();
        System.out.println("App Closed.");
    }
}
