package logiciel;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Popup;
  
public class test extends Application {
  
    // launch the application
    public void start(Stage stage)
    {
  
        // set title for the stage
        stage.setTitle("Creating Popup");
  
        // create a button
        Button button = new Button("popup");
  
        // create a tile pane
        TilePane tilepane = new TilePane();
  
        // create a label
        Label label = new Label("This is a Popup");
  
        // create a popup
        Popup popup = new Popup();
  
        // set background
        label.setStyle(" -fx-background-color: pink;");
  
        // add the label
        popup.getContent().add(label);
  
        // set size of label
        label.setMinWidth(80);
        label.setMinHeight(50);
  
        // set auto hide
        popup.setAutoHide(true);
  
        // action event
        EventHandler<ActionEvent> event = 
        new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                if (!popup.isShowing())
                    popup.show(stage);
            }
        };
  
        // when button is pressed
        button.setOnAction(event);
  
        // add button
        tilepane.getChildren().add(button);
  
        // create a scene
        Scene scene = new Scene(tilepane, 200, 200);
  
        // set the scene
        stage.setScene(scene);
  
        stage.show();
    }
  
    // Main Method
    public static void main(String args[])
    {
  
        // launch the application
        launch(args);
    }
}
