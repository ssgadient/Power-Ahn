package powered_tasks;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.time.LocalDateTime;

public class MainBackgroundThread extends Thread{
   //does a Database query every 5 seconds
   //does a System date time check every second
   //adds tasks from the database to worker threads so that they can execute
   //figure out how to open a timer on the UI when the tasks execute
    private boolean stopThread = false;
    ScheduledExecutorService taskExecutor = Executors.newScheduledThreadPool(10);
    //HashMap<Integer, Task> executedTasks = new HashMap<Integer, Task>();
    ArrayList<Task> executedTasks = new ArrayList<Task>();

    class DBQuery implements Runnable{
        public void run(){
            ArrayList<Task> queriedTasks = TaskDatabase.readClosestDate(1);
            for (Task task : queriedTasks){
                System.out.println(task.toString());
                if (!executedTasks.contains(task)){
                    long delayTimeSeconds = (task.getStartTime().toLocalTime().toSecondOfDay()) - (LocalDateTime.now().toLocalTime().toSecondOfDay());
                    System.out.println(delayTimeSeconds);
                    taskExecutor.schedule(new TaskRunner(task), delayTimeSeconds, TimeUnit.SECONDS);
                    executedTasks.add(task);
                    System.out.println(executedTasks.toString());
                }
            }
        }
    }

    public synchronized void doStop() {
        this.stopThread = true;
    }

    private synchronized boolean getStop() {
       return this.stopThread;
    }

    public void run(){
        ScheduledFuture<?> queryHandle = taskExecutor.scheduleAtFixedRate(new DBQuery(), 3, 5, TimeUnit.SECONDS);
        
        /* 
        Timer dbQueryTimer = new Timer();
        dbQueryTimer.schedule(new DBQueryTask(), 200, 5000);*/

        while(getStop() == false) { 
            //System.out.println("Thread running!");
        }

        taskExecutor.shutdown();
        System.out.println("Thread stopped!");
        return; 
    }


    /* 
    long startTime = System.currentTimeMillis();
    while(true){
        Task t;
        Date d;
        Timer timer = new Timer();
        TaskRunner tr = new TaskRunner(t);
        //timer.schedule(tr, d);
        try {
            //Wait for one sec so it doesn't print too fast
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    */
}

/* 
class DBQueryTask extends TimerTask{
    public void run(){
        Callable<ArrayList<Task>> dbQuery = () -> {
            System.out.println("Querying database...");
            //return TaskDatabase.readClosestDate;
        };

        //<ArrayList<Task>> ScheduledFuture<ArrayList<Task>> = taskExecutor.schedule(dbQuery, 1, TimeUnit.SECONDS)
    }
}*/