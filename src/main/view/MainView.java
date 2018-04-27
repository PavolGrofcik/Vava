package main.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.controller.Controller;

public class MainView extends Application {

	private Button Button = new Button("Hello world");
	
	@Override
	public void start(Stage primaryStage) {
		
		Controller controller = Controller.getInstance();
		
		Pane pane = new Pane();
		pane.getChildren().addAll(Button);
		
		
		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		Button.setLayoutX(500);
		Button.setLayoutY(300);
		
		Button.setOnAction(e->{
			System.out.println("Hello world");
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}
