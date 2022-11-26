package powered_tasks;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.time.LocalDateTime;

public class MainBackgroundThread extends Thread{
   //does a Database query every 5 seconds
   //does a System date time check every second
   //adds tasks from the database to worker threads so that they can execute
   //figure out how to open a timer on the UI when the tasks execute
    private boolean stopThread = false;
    ScheduledExecutorService taskExecutor = Executors.newScheduledThreadPool(10);
    ArrayList<String> executedTasks = new ArrayList<String>();
    static String timerText = "";
    static int timerSeconds;

    class DBQuery implements Runnable{
        public void run(){
            ArrayList<Task> queriedTasks = TaskDatabase.readClosestDate(5);
            //for testing //System.out.println(queriedTasks.toString()); 
            for (Task task : queriedTasks){
                if (!executedTasks.contains(task.getTaskUUID())){
                    long delayTimeSeconds = (task.getStartTime().toLocalTime().toSecondOfDay()) - (LocalDateTime.now().toLocalTime().toSecondOfDay());
                    int taskTime = (task.getEndTime().toLocalTime().toSecondOfDay()) - (task.getStartTime().toLocalTime().toSecondOfDay());
                    //System.out.println(delayTimeSeconds);
                    timerSeconds = taskTime;
                    taskExecutor.schedule(new TaskRunner(task), delayTimeSeconds, TimeUnit.SECONDS);
                    //taskExecutor.scheduleAtFixedRate(new TimerUpdater(), 0, 1, TimeUnit.SECONDS);
                    executedTasks.add(task.getTaskUUID());
                    //System.out.println(executedTasks.toString());
                }
            }
        }
    }

    /* 
    class TimerUpdater implements Runnable {
        @Override
        public void run() {
            int h = timerSeconds / 3600;
            int m = (timerSeconds % 3600) / 60;
            int s = timerSeconds % 60;
            String hours = Integer.toString(h);
            String minutes = Integer.toString(m);
            String seconds = Integer.toString(s);
            if (h < 10) {
                hours = "0" + h;
            }
            if (m < 10) {
                minutes = "0" + m;
            }
            if (s < 10) {
                seconds = "0" + s;
            }
            timerText = "" + hours + ":" + minutes + ":" + seconds;
            timerSeconds--;
            //setSeconds(getSeconds() - 1);
            if (timerSeconds < 0) {
                //timer.cancel();
            }
        }

         
        public int getSeconds() {
            return timerSeconds;
        }

        public void setSeconds(int innerSeconds) {
            this.Seconds = innerSeconds;
        }
    }*/


    public synchronized void doStop() {
        this.stopThread = true;
    }

    private synchronized boolean getStop() {
       return this.stopThread;
    }

    public void run(){
        taskExecutor.scheduleAtFixedRate(new DBQuery(), 3, 5, TimeUnit.SECONDS);

        while(getStop() == false) { 
            //System.out.println("Thread running!");
        }

        taskExecutor.shutdown();
        System.out.println("Thread stopped!");
        return; 
    }

    public synchronized static String getTimerText(){
        return timerText;
    }
}

