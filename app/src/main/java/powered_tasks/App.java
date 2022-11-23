package powered_tasks;


public class App {
    public static void main(String[] args) {
        MainBackgroundThread mbt = new MainBackgroundThread();
        mbt.start();

        MainFrame.openApp(args);

        mbt.doStop();
        System.out.println("App Closed.");
    }
}
