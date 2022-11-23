import java.util.Timer;
import java.util.TimerTask;

public class CountdownTimer {
    public void countdown(int totalSeconds){
        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask(){           @Override
            public void run(){
                //TimerUI.updateTimer(totalSeconds);
                if (totalSeconds < 0){
                    timer.cancel();
                }
            }
        }, 0, 1000);
    }
}
