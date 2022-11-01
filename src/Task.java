import java.time.*;
// import java.net.URL;
// import java.io.File;

public class Task {
    private String taskName;
    private Duration estimatedDuration;
    private String appID;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String taskDetails;
    private String taskSubject;
    private int taskPoints;
    private LocalDateTime deadline;
    // private URL taskLink;
    // private File taskFile;

    public Task(String taskName) {
        this.taskName = taskName;
    }

    public Task(String taskName, Duration estimatedDuration) {
        this.taskName = taskName;
        this.estimatedDuration = estimatedDuration;
        this.appID = null;
        this.startTime = null;
        this.endTime = null;
        this.taskDetails = null;
        this.taskSubject = null;
        this.taskPoints = 0;
        this.deadline = null;
    }

    public Duration calculateEstimatedDuration(LocalDateTime start, LocalDateTime end) {
        return Duration.between(start, end);
    }

    public String getTaskName() {
        return this.taskName;
    }

    public Duration getEstimatedDuration() {
        return estimatedDuration;
    }

    public String getAppID() {
        return this.appID;
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    public String getTaskDetails() {
        return this.taskDetails;
    }

    public String getTaskSubject() {
        return this.taskSubject;
    }

    public int getTaskPoints() {
        return this.taskPoints;
    }

    public LocalDateTime getDeadline() {
        return this.deadline;
    }
    
    public void showUI() {

    }
}
