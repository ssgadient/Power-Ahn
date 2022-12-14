package powered_tasks;

import java.time.*;
// import java.net.URL;
// import java.io.File;
import java.util.UUID;

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
    private String taskUUID;
    // private URL taskLink;
    // private File taskFile;

    public Task(String taskName) {
        this.taskName = taskName;
    }

    // Removed estimatedDuration from parameters, because can only calculate it after creating the task. 
    public Task(String taskName, LocalDateTime taskStart, LocalDateTime taskEnd, String appID, String taskUUID) {
        this.taskName = taskName;
        this.taskStart = taskStart;
        this.taskEnd = taskEnd;
        this.estimatedDuration = null; // Set to null for now. We cannot store estimated duration if we cannot calculate it to begin wit. 
        this.appID = appID;
        this.taskDetails = null;
        this.taskSubject = null;
        this.taskPoints = 0;
        this.deadline = null;
        this.taskUUID = taskUUID;
    }

    /*
    public Task(String taskName, LocalDateTime startTime, LocalDateTime endTime) {
        this.taskName = taskName;
        this.appID = null;
        this.taskStart = startTime;
        this.taskEnd = endTime;
        this.taskDetails = null;
        this.taskSubject = null;
        this.taskPoints = 0;
        this.deadline = null;
    }*/

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

    public String getTaskUUID(){
        return this.taskUUID;
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

    public void setTaskUUID(String taskUUID){
        this.taskUUID = taskUUID;
    }

    public void showUI() {

    }

    public String toString(){
        return "Taskname: " + taskName + ", Start Time: " + taskStart + ", End Time: " + taskEnd + ", App ID: " + appID + ", Task UUID: " + taskUUID;
    }
}
