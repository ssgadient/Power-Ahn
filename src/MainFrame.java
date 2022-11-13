import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MainFrame extends Application {
    public static void openApp(String[] args) {
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
        root.getChildren().add(createTimer("2022-11-08 11:00:00", "2022-11-08 19:00:00"));
        Scene background = new Scene(root, 1000, 700);
        background.setFill(Color.rgb(250, 225, 180));

        primaryStage.setTitle("MainFrame");
        primaryStage.setScene(background);
        primaryStage.setMaximized(true);
        primaryStage.show();
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
                tb1.setLayoutX(120); tb1.setLayoutY(60);

                Text t2 = new Text("Start Time*"); 
                t2.setX(50); t2.setY(125);
                t2.setFont(Font.font("Times New Roman", 14));
                TextField tb2 = new TextField();
                tb2.setLayoutX(120); tb2.setLayoutY(110);
                tb2.setPrefWidth(80);

                ToggleGroup startampm = new ToggleGroup();
                RadioButton startam = new RadioButton("AM");
                startam.setLayoutX(210); startam.setLayoutY(113);
                startam.setSelected(true);
                startam.setToggleGroup(startampm);
                RadioButton startpm = new RadioButton("PM");
                startpm.setToggleGroup(startampm);
                startpm.setLayoutX(255); startpm.setLayoutY(113);

                Text t3 = new Text("End Time*"); 
                t3.setX(50); t3.setY(175);
                t3.setFont(Font.font("Times New Roman", 14));
                TextField tb3 = new TextField();
                tb3.setLayoutX(120); tb3.setLayoutY(160);
                tb3.setPrefWidth(80);

                ToggleGroup startampm2 = new ToggleGroup();
                RadioButton startam2 = new RadioButton("AM");
                startam2.setLayoutX(210); startam2.setLayoutY(163);
                startam2.setSelected(true);
                startam2.setToggleGroup(startampm2);
                RadioButton startpm2 = new RadioButton("PM");
                startpm2.setToggleGroup(startampm2);
                startpm2.setLayoutX(255); startpm2.setLayoutY(163);

                Text t4 = new Text("App Name");
                t4.setX(50); t4.setY(225);
                t4.setFont(Font.font("Times New Roman", 14));
                TextField tb4 = new TextField();
                tb4.setLayoutX(120); tb4.setLayoutY(210);

                Button submit = new Button("Add Task");
                submit.setLayoutX(300); submit.setLayoutY(250);

                Group root = new Group(t1, tb1, t2, tb2, t3, tb3, t4, tb4, submit, startam, startpm, startam2, startpm2);
                Scene background = new Scene(root, 400, 350);
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
                            Text errorText = new Text("One or more required \n fields are missing!");
                            errorText.setX(30); errorText.setY(55);
                            errorText.setFont(Font.font ("Times New Roman", 16));
                            errorText.setFill(Color.RED);
                            Group error = new Group(errorText);
                            Scene background = new Scene(error, 200, 150);
                            Stage errorStage = new Stage();
                            errorStage.setTitle("Error");
                            errorStage.setScene(background);
                            errorStage.showAndWait();
                        }
                        else if (!appName.equals("") && !appIDs.containsKey(appName)){
                            Text errorText = new Text("Please enter a \n valid app name!");
                            errorText.setX(30); errorText.setY(55);
                            errorText.setFont(Font.font ("Times New Roman", 16));
                            errorText.setFill(Color.RED);
                            Group error = new Group(errorText);
                            Scene background = new Scene(error, 200, 150);
                            Stage errorStage = new Stage();
                            errorStage.setTitle("Error");
                            errorStage.setScene(background);
                            errorStage.showAndWait();
                        }
                        else {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            //Task task = new Task(taskName, Duration.between(LocalDateTime.parse(startTime, formatter), LocalDateTime.parse(endTime, formatter)));
                            System.out.println("Worked!");
                            taskStage.close();
                        }

                    }

                });

                taskStage.setTitle("taskFrame");
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
                System.out.println("What time would you like?");
            }

        });
        buttonList.add(setTimer);

        return buttonList;
    }

    public Text createTimer(String before, String after) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Duration d = Duration.between(LocalDateTime.parse(before, formatter), LocalDateTime.parse(after, formatter));
        int seconds = (int) d.getSeconds();
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