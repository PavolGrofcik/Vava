package main.view;


import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
<<<<<<< HEAD
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
=======
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
>>>>>>> b597dfea3e58c30ceb8a194d30d0e5dd767a9b1c
import javafx.stage.Stage;
import main.controller.Controller;


public class MainView extends Application {

	private Label user = new Label("Username");
	private Label password = new Label("Password");
	private TextField user_text = new TextField();
	private PasswordField password_text = new PasswordField();
	private Button login = new Button("Login");
	Image background = new Image("File:lib/background.png");
	ImageView iv = new ImageView();
	
	
	@Override
	public void start(Stage primaryStage) throws Exception  {
		
		Controller controller = Controller.getInstance();
		Pane pane = new Pane();
		
		iv.setImage(background);
		pane.getChildren().add(iv);
		
		pane.getChildren().add(user);
		user.setLayoutX(100);
		user.setLayoutY(105);
		user.setFont(Font.font(null, FontWeight.BOLD, 14));
		
<<<<<<< HEAD
		Pane pane = new Pane();
		pane.getChildren().addAll(Button);
=======
		pane.getChildren().add(password);
		password.setLayoutX(100);
		password.setLayoutY(155);
		password.setFont(Font.font(null, FontWeight.BOLD, 14));
>>>>>>> b597dfea3e58c30ceb8a194d30d0e5dd767a9b1c
		
		pane.getChildren().add(user_text);
		user_text.setLayoutX(200);
		user_text.setLayoutY(100);
		user_text.setScaleX(1.2);
		user_text.setScaleY(1.2);	
		
		pane.getChildren().add(password_text);
		password_text.setLayoutX(200);
		password_text.setLayoutY(150);
		password_text.setScaleX(1.2);
		password_text.setScaleY(1.2);
		
		pane.getChildren().add(login);
		login.setLayoutX(220);
		login.setLayoutY(200);
		login.setScaleX(1.2);
		login.setScaleY(1.2);
		login.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				System.out.println("funguj");
			}
		});
				
		Scene scene = new Scene(pane,450,300);
		primaryStage.setTitle("Login");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		
<<<<<<< HEAD
		Button.setLayoutX(500);
		Button.setLayoutY(300);
		
		Button.setOnAction(e->{
			System.out.println("Hello world");
		});
=======
>>>>>>> b597dfea3e58c30ceb8a194d30d0e5dd767a9b1c
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	
	
}
