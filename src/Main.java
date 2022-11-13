import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class Main {
    public static void main(String[] args) {     
        MainBackgroundThread mbt = new MainBackgroundThread();
        ScheduledExecutorService taskExecutor = Executors.newScheduledThreadPool(10);
        mbt.start();

        MainFrame.openApp(args);

        mbt.doStop();
        System.out.println("App Closed.");
    }
}