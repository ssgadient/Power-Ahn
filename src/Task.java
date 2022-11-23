import java.time.*;
// import java.net.URL;
// import java.io.File;

public class Task {
    private String taskName;
    private Duration estimatedDuration;
    private String appID;
    private LocalDateTime taskStart;
    private LocalDateTime taskEnd;
    private String taskDetails;
    private String taskSubject;
    private int taskPoints;
    private LocalDateTime deadline;
    // private URL taskLink;
    // private File taskFile;

    public Task(String taskName) {
        this.taskName = taskName;
    }

    // Removed estimatedDuration from parameters, because can only calculate it after creating the task. 
    public Task(String taskName, LocalDateTime taskStart, LocalDateTime taskEnd, String appID) {
        this.taskName = taskName;
        this.taskStart = taskStart;
        this.taskEnd = taskEnd;
        this.estimatedDuration = null; // Set to null for now. We cannot store estimated duration if we cannot calculate it to begin wit. 
        this.appID = appID;
        this.taskDetails = null;
        this.taskSubject = null;
        this.taskPoints = 0;
        this.deadline = null;
    }

    public Task(String taskName, LocalDateTime startTime, LocalDateTime endTime, String appID) {
        this.taskName = taskName;
        this.appID = appID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.taskDetails = null;
        this.taskSubject = null;
        this.taskPoints = 0;
        this.deadline = null;
    }

    // Converting to an int for duration to be able to store into database
    public Duration calculateEstimatedDuration(LocalDateTime start, LocalDateTime end) {
        return Duration.between(start, end);
    }

    public String getTaskName() {
        return this.taskName;
    }

    public Duration getEstimatedDuration() {
        return this.estimatedDuration;
    }

    public String getAppID() {
        return this.appID;
    }

    public LocalDateTime getStartTime() {
        return this.taskStart;
    }

    public LocalDateTime getEndTime() {
        return this.taskEnd;
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

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setEstimatedDuration(Duration estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.taskStart = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.taskEnd = endTime;
    }

    public void setTaskDetails(String taskDetails) {
        this.taskDetails = taskDetails;
    }

    public void setTaskSubject(String taskSubject) {
        this.taskSubject = taskSubject;
    }

    public void setTaskPoints(int taskPoints) {
        this.taskPoints = taskPoints;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public void showUI() {

    }
}
