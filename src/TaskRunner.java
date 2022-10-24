import java.io.IOException;
import java.util.TimerTask;

public class TaskRunner extends TimerTask{
    Task task;

    public TaskRunner(Task task){
        this.task = task;
    }

    public void run(){
        String appRunCommand = "explorer shell:Appsfolder\\" + task.appID;
        try {
            Process process = Runtime.getRuntime().exec(new String[]{appRunCommand});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
