import java.time.*;

import java.util.*;

public class TimerUI {
    
    Task t;
    Duration d;
    int seconds;
    String timeLeft;

    TimerUI(Task t) {
        this.t = t;
        this.d = t.getEstimatedDuration();
        this.seconds = (int) d.getSeconds();
    }

    public String updateTimer() {

        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {
                int h = seconds / 3600;
                int m = (seconds % 3600) / 60;
                int s = seconds % 60;
                
                timeLeft = "" + h + ":" + m + ":" + s;
                seconds -= 1;
                System.out.println(timeLeft);
            }

        };


        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 10000);
        if (seconds < 0) {
            timer.cancel();
        }
        return timeLeft;
    }

}
