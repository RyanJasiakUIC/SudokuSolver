
import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SudokuApp extends Application {
	
	ListView<String> listItems, listItems2;

    @Override
    public void start(Stage primaryStage) throws Exception {

        //
        // Set the title at the top of the window
        primaryStage.setTitle("YOUR TITLE GOES HERE");

        //
        // Set the initial fxml file:
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/FXML.fxml"));

        //
        // Initialize start the app:
        Scene s1 = new Scene(root, 700, 700);
        s1.getStylesheets().add("/styles/style1.css");
        primaryStage.setScene(s1);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
		launch(args);
	}
    
}