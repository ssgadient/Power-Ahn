import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        t.setX(100); t.setY(25);
        t.setFont(Font.font ("Times New Roman", 20));
        t.setFill(Color.BLACK);
        
        Group root = new Group(t);
        List<Button> buttons = createButtons();
        for (Button b: buttons) {
            root.getChildren().add(b);
        }

        Scene background = new Scene(root, 1000, 500);
        background.setFill(Color.rgb(250, 225, 180));

        primaryStage.setTitle("MainFrame");
        primaryStage.setScene(background);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public List<Button> createButtons() {
        List<Button> buttonList = new ArrayList<Button>();

        Button createTask = new Button();
        createTask.setText("Create A New Task");
        createTask.setLayoutX(600); createTask.setLayoutY(400);
        createTask.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                Task newTask = new Task("First Task");
            }
        });
        buttonList.add(createTask);

        Button setTimer = new Button();
        setTimer.setText("Set Timer");
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