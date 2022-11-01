import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;

public class MainFrame extends Application {
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
                ToggleButton startam = new ToggleButton("AM");
                startam.setLayoutX(200); startam.setLayoutY(110);
                startam.setToggleGroup(startampm);
                ToggleButton startpm = new ToggleButton("PM");
                startpm.setToggleGroup(startampm);
                startpm.setLayoutX(235); startpm.setLayoutY(110);

                Text t3 = new Text("End Time*"); 
                t3.setX(50); t3.setY(175);
                t3.setFont(Font.font("Times New Roman", 14));
                TextField tb3 = new TextField();
                tb3.setLayoutX(120); tb3.setLayoutY(160);

                Text t4 = new Text("App Name");
                t4.setX(50); t4.setY(225);
                t4.setFont(Font.font("Times New Roman", 14));
                TextField tb4 = new TextField();
                tb4.setLayoutX(120); tb4.setLayoutY(210);

                Button submit = new Button("Add Task");
                submit.setLayoutX(300); submit.setLayoutY(250);

                Group root = new Group(t1, tb1, t2, tb2, t3, tb3, t4, tb4, submit, startam, startpm);
                Scene background = new Scene(root, 400, 350);
                Stage taskStage = new Stage();

                submit.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        String taskName = tb1.getText();
                        String startTime = tb2.getText();
                        String endTime = tb3.getText();
                        String appName = tb4.getText();

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
                        else {
                            Task task = new Task(taskName);
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

}