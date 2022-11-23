package powered_tasks;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MainFrame extends Application {

    Text mainTimer = new Text("");
    Stage primaryStage = new Stage();

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {

        Text t = new Text("Welcome to the Power-Ahn Project, a productivity tool to make you power on!");
        t.setX(100); t.setY(50);
        t.setFont(Font.font ("Times New Roman", 20));
        t.setFill(Color.BLACK);

        Group root = new Group(t);
        List<Button> buttons = createButtons();
        for (Button b: buttons) {
            root.getChildren().add(b);
        }
        root.getChildren().add(mainTimer);
        Scene background = new Scene(root, 1600, 800);
        background.setFill(Color.rgb(250, 225, 180));

        this.primaryStage.setTitle("MainFrame");
        this.primaryStage.setScene(background);
        this.primaryStage.setMaximized(true);
        this.primaryStage.show();
    }

    public List<Button> createButtons() {
        List<Button> buttonList = new ArrayList<Button>();

        Button createTask = new Button("Create New Task");
        createTask.setLayoutX(600); createTask.setLayoutY(400);
        createTask.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Text t1 = new Text("Task name*"); 
                t1.setX(50); t1.setY(75);
                t1.setFont(Font.font("Times New Roman", 14));
                TextField tb1 = new TextField();
                tb1.setLayoutX(130); tb1.setLayoutY(60);

                Text t2 = new Text("Start Time*"); 
                t2.setX(50); t2.setY(125);
                t2.setFont(Font.font("Times New Roman", 14));
                TextField tb2 = new TextField();
                tb2.setLayoutX(130); tb2.setLayoutY(110);
                Text t2a = new Text("(DD-MM-YYYY HH:MM AM/PM)");
                t2a.setX(290); t2a.setY(125);
                t2a.setFont(Font.font("Times New Roman", 12));

                Text t3 = new Text("End Time*"); 
                t3.setX(50); t3.setY(175);
                t3.setFont(Font.font("Times New Roman", 14));
                TextField tb3 = new TextField();
                tb3.setLayoutX(130); tb3.setLayoutY(160);
                Text t3a = new Text("(DD-MM-YYYY HH:MM AM/PM)");
                t3a.setX(290); t3a.setY(175);
                t3a.setFont(Font.font("Times New Roman", 12));

                Text t4 = new Text("App Name");
                t4.setX(50); t4.setY(225);
                t4.setFont(Font.font("Times New Roman", 14));
                TextField tb4 = new TextField();
                tb4.setLayoutX(130); tb4.setLayoutY(210);

                Button submit = new Button("Add Task");
                submit.setLayoutX(300); submit.setLayoutY(250);

                Group root = new Group(t1, tb1, t2, tb2, t2a, t3, tb3, t3a, t4, tb4, submit);
                Scene background = new Scene(root, 600, 500);
                Stage taskStage = new Stage();

                submit.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        String taskName = tb1.getText();
                        String startTime = tb2.getText();
                        String endTime = tb3.getText();
                        String appName = tb4.getText();

                        HashMap<String, String> appIDs = AUMIDRetriever.getAUMIDs();

                        if (taskName.equals("") || startTime.equals("") || endTime.equals("")) {
                            showMessage("One or more required \n fields are missing!", "error");
                        }
                        else if (!appName.equals("") && !appIDs.containsKey(appName)){
                            showMessage("Please enter a \n valid app name!", "error");
                        }
                        else {
                            try {
                                System.out.println(formatTime(startTime)); System.out.println(formatTime(endTime));
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                LocalDateTime start = LocalDateTime.parse(formatTime(startTime), formatter);
                                LocalDateTime end = LocalDateTime.parse(formatTime(endTime), formatter);
                                if (Duration.between(start, end).compareTo(Duration.ZERO) < 0) {
                                    showMessage("End Time must come after start time", "error");
                                }
                                else {
                                    Task task = new Task(taskName, start, end, appName);
                                    mainTimer = createTimer((int) Duration.between(start, end).getSeconds());
                                    showMessage("Successfully created\n Task \"" + taskName + "\"!", "success");
                                    start(primaryStage);
                                    primaryStage.setMaximized(true);
                                    taskStage.close();
                                    TaskRunner runner = new TaskRunner(task);
                                    runner.run();
                                }
                            } catch(Exception e) {
                                showMessage("Incorrect Date Format", "error");
                            }
                        }

                    }

                    public String formatTime(String s) {
                        if (s.matches("\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2} [a-zA-Z][a-zA-Z]") == false) {
                            return null;
                        }
                        String[] dateComponents = s.split("-");
                        String reverseDate = dateComponents[2].substring(0, 4) + "-" + dateComponents[1] + "-" + dateComponents[0];
                        reverseDate += dateComponents[2].substring(4, dateComponents[2].length() - 3) + ":00";
                        if (s.substring(s.length() - 2, s.length()).toUpperCase().equals("PM")) {
                            int hours = Integer.parseInt(reverseDate.substring(reverseDate.length() - 8, reverseDate.length() - 6));
                            if (hours != 12) {
                                hours += 12;
                            }
                            reverseDate = reverseDate.substring(0, reverseDate.length() - 8) + hours + reverseDate.substring(reverseDate.length() - 6, reverseDate.length());
                        }
                        else if (s.substring(s.length() - 2, s.length()).toUpperCase().equals("AM")) {
                            int hours = Integer.parseInt(reverseDate.substring(reverseDate.length() - 8, reverseDate.length() - 6));
                            if (hours == 12) {
                                hours -= 12;
                            }
                            if (hours == 10 || hours == 11) {
                                reverseDate = reverseDate.substring(0, reverseDate.length() - 8) + hours + reverseDate.substring(reverseDate.length() - 6, reverseDate.length());
                            }
                            reverseDate = reverseDate.substring(0, reverseDate.length() - 8) + "0" + hours + reverseDate.substring(reverseDate.length() - 6, reverseDate.length());
                        }
                        return reverseDate;
                    }



                });

                taskStage.setTitle("New Task");
                taskStage.setScene(background);
                taskStage.show();
            }

        });

        buttonList.add(createTask);

        Button setTimer = new Button("Set Timer");
        setTimer.setLayoutX(800); setTimer.setLayoutY(400);
        setTimer.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Text t1 = new Text("Hours"); 
                t1.setX(50); t1.setY(75);
                t1.setFont(Font.font("Times New Roman", 14));
                TextField tb1 = new TextField();
                tb1.setLayoutX(130); tb1.setLayoutY(60);

                Text t2 = new Text("Minutes"); 
                t2.setX(50); t2.setY(125);
                t2.setFont(Font.font("Times New Roman", 14));
                TextField tb2 = new TextField();
                tb2.setLayoutX(130); tb2.setLayoutY(110);

                Text t3 = new Text("Seconds"); 
                t3.setX(50); t3.setY(175);
                t3.setFont(Font.font("Times New Roman", 14));
                TextField tb3 = new TextField();
                tb3.setLayoutX(130); tb3.setLayoutY(160);

                Button startTimer = new Button("Start");
                startTimer.setLayoutX(300); startTimer.setLayoutY(250);

                Group root = new Group(t1, tb1, t2, tb2, t3, tb3, startTimer);
                Scene background = new Scene(root, 600, 500);
                Stage setTimerStage = new Stage();
                setTimerStage.setTitle("Set Timer");
                setTimerStage.setScene(background);
                setTimerStage.show();

                startTimer.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        String hours = tb1.getText();
                        String minutes = tb2.getText();
                        String seconds = tb3.getText();
                        try {
                            int h = Integer.parseInt(hours);
                            int m = Integer.parseInt(minutes);
                            int s = Integer.parseInt(seconds);
                            if (h < 0 || m < 0 || s < 0) {
                                showMessage("Please enter\n positive integers", "error");
                            }
                            else {
                                mainTimer = createTimer(3600*Integer.parseInt(hours) + 60*Integer.parseInt(minutes) + Integer.parseInt(seconds));
                                start(primaryStage);
                                setTimerStage.close();
                            }
                        } catch(NumberFormatException e) {
                            showMessage("Please enter\n positive integers", "error");
                        }
                    }
                });

            }
        });
        buttonList.add(setTimer);

        return buttonList;
    }

    public void showMessage(String message, String reason) {
        Text t = new Text(message);
        t.setX(30); t.setY(55);
        t.setFont(Font.font ("Times New Roman", 16));
        if (reason.equals("error")) {
            t.setFill(Color.RED);
        }
        else {
            t.setFill(Color.GREEN);
        }
        Group text = new Group(t);
        Scene background = new Scene(text, 200, 150);
        Stage errorStage = new Stage();
        errorStage.setScene(background);
        errorStage.showAndWait();
    }

    public Text createTimer(int seconds) {
        Text timerText = new Text("");
        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            public int innerSeconds = seconds;

            @Override
            public void run() {
                int h = innerSeconds / 3600;
                int m = (innerSeconds % 3600) / 60;
                int s = innerSeconds % 60;
                String hours = Integer.toString(h);
                String minutes = Integer.toString(m);
                String seconds = Integer.toString(s);
                if (h < 10) {
                    hours = "0" + h;
                }
                if (m < 10) {
                    minutes = "0" + m;
                }
                if (s < 10) {
                    seconds = "0" + s;
                }
                timerText.setText("" + hours + ":" + minutes + ":" + seconds);
                setSeconds(getSeconds() - 1);
                if (innerSeconds < 0) {
                    timer.cancel();
                }
            }

            public int getSeconds() {
                return innerSeconds;
            }

            public void setSeconds(int innerSeconds) {
                this.innerSeconds = innerSeconds;
            }
        };

        timer.scheduleAtFixedRate(timerTask, 0, 1000);
        timerText.setX(700); timerText.setY(200);
        timerText.setFont(Font.font("Times New Roman", 60));
        timerText.setFill(Color.GREEN);
        return timerText;
    }

}