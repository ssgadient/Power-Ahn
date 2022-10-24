import java.time.*;
import java.net.URL;
import java.io.File;

public class Task {
    private String taskName;
    private String taskSubject;
    private int taskPoints;
    private String taskDetails;
    private LocalDateTime dueTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Duration estimatedDuration;
    //private URL taskLink;
    //private File taskFile;
    private String appID;

    //we need a whole bunch of constructors for the many different task creation possibilities

    public Task(String taskName, LocalDateTime dueTime, String appID){
        this.taskName = taskName;
        this.dueTime = dueTime;
        this.appID = appID;
    }

    public Task(String taskName, Duration estimatedDuration){
        this.taskName = taskName;
        this.estimatedDuration = estimatedDuration;
    }

    public Task(String taskName, LocalDateTime startTime, LocalDateTime endTime){
        this.taskName = taskName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.estimatedDuration = calculateEstimatedDuration(startTime, endTime);
    }

    public Duration calculateEstimatedDuration(LocalDateTime start, LocalDateTime end){
        return Duration.between(start, end);
    }

    public String getTaskName(){
        return this.taskName;
    }

    public LocalDateTime getDueTime(){
        return this.dueTime;
    }

    public LocalDateTime getStartTime(){
        return this.startTime;
    }

    public LocalDateTime getEndTime(){
        return this.endTime;
    }

    public Duration getEstimatedDuration(){
        return estimatedDuration;
    }

    public String getAppID(){
        return this.appID;
    }
}
