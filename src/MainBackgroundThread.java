//import java.util.Date;
import java.util.Timer;
//import java.util.TimerTask;

public class MainBackgroundThread extends Thread{
   //does a Database query every 5 seconds
   //does a System date time check every second
   //adds tasks from the database to worker threads so that they can execute
   //figure out how to open a timer on the UI when the tasks execute
   private boolean stopThread = false;

   public synchronized void doStop() {
       this.stopThread = true;
   }

   private synchronized boolean getStop() {
       return this.stopThread;
   }

    public void run(){
        Timer dbQueryTimer = new Timer();

        while(getStop() == false) { 
            System.out.println("Thread running!");
            
        }

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