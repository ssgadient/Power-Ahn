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

    class DBQuery implements Runnable{
        public void run(){
            ArrayList<Task> queriedTasks = TaskDatabase.readClosestDate(5, "taskName, startDate, endDate, duration, appID, taskUUID");
            //for testing //System.out.println(queriedTasks.toString()); 
            for (Task task : queriedTasks){
                if (!executedTasks.contains(task.getTaskUUID())){
                    long delayTimeSeconds = (task.getStartTime().toLocalTime().toSecondOfDay()) - (LocalDateTime.now().toLocalTime().toSecondOfDay());
                    //System.out.println(delayTimeSeconds);
                    taskExecutor.schedule(new TaskRunner(task), delayTimeSeconds, TimeUnit.SECONDS);
                    executedTasks.add(task.getTaskUUID());
                    //System.out.println(executedTasks.toString());
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
        taskExecutor.scheduleAtFixedRate(new DBQuery(), 3, 5, TimeUnit.SECONDS);

        while(getStop() == false) { 
            //System.out.println("Thread running!");
        }

        taskExecutor.shutdown();
        System.out.println("Thread stopped!");
        return; 
    }
}

