package main.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.controller.Controller;

public class MainView extends Application {

	private Button Button = new Button("Hello world");
	
	@Override
	public void start(Stage primaryStage) {
		
		Controller controller = Controller.getInstance();
		
		BorderPane pane = new BorderPane();
		
		
		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
