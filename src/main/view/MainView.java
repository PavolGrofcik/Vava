package main.view;


import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import java.security.NoSuchAlgorithmException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import main.controller.Controller;


public class MainView extends Application {

	private Label user = new Label("Username");
	private Label password = new Label("Password");
	private TextField user_text = new TextField();
	private PasswordField password_text = new PasswordField();
	private Button login = new Button("Login");
	Image background = new Image("File:resource/login2.png");
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
		
		pane.getChildren().add(password);
		password.setLayoutX(100);
		password.setLayoutY(155);
		password.setFont(Font.font(null, FontWeight.BOLD, 14));

		
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
				try {
					int status = controller.loginCustomer(user_text, password_text);
					if(status == 1) {
						
					}
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	
		Scene scene = new Scene(pane,450,300);
		primaryStage.setTitle("Login");
		primaryStage.getIcons().add(new Image("File:resource/logo.png"));
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}		

	public static void main(String[] args) {
		launch(args);
	}
	
	
	
}
