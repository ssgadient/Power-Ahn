package powered_tasks;

import java.io.IOException;

public class TaskRunner implements Runnable{
    Task task;

    public TaskRunner(Task task){
        this.task = task;
    }

    public void run(){
        String appRunCommand = "explorer shell:Appsfolder\\" + task.getAppID();
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"cmd", "/c", appRunCommand});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
