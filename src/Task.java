import java.time.*;
import java.net.URL;
import java.io.File;
import java.time.format.DateTimeFormatter;

public class Task {
    public String taskName;
    public String taskSubject;
    public int taskPoints;
    public String taskDetails;
    public LocalDateTime dueTime;
    public LocalDateTime startTime;
    public LocalDateTime endTime;
    public Duration estimatedDuration;
    // private URL taskLink;
    // private File taskFile;
    public String appID;

    // we need a whole bunch of constructors for the many different task creation
    // possibilities

    public Task(String taskName, LocalDateTime dueTime, String appID) {
        // Reformate dueTime to YYYY-MM-DD format
        DateTimeFormatter reformateDate = DateTimeFormatter.ofPattern("YYYY-MM-dd");
        dueTime.format(reformateDate);

        this.taskName = taskName;
        this.dueTime = dueTime;
        this.appID = appID;
    }

    public Task(String taskName, Duration estimatedDuration) {
        this.taskName = taskName;
        this.estimatedDuration = estimatedDuration;
    }

    public Task(String taskName, LocalDateTime startTime, LocalDateTime endTime) {
        this.taskName = taskName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.estimatedDuration = calculateEstimatedDuration(startTime, endTime);
    }

    public Duration calculateEstimatedDuration(LocalDateTime start, LocalDateTime end) {
        return Duration.between(start, end);
    }

    public String getTaskName() {
        return this.taskName;
    }

    public String getTaskSubject() {
        return this.taskSubject;
    }

    public int getTaskPoints() {
        return this.taskPoints;
    }

    public String getTaskDetails() {
        return this.taskDetails;
    }

    public LocalDateTime getDueTime() {
        return this.dueTime;
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    public Duration getEstimatedDuration() {
        return estimatedDuration;
    }

    public String getAppID() {
        return this.appID;
    }
}
