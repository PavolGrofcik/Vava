package main.view;


import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import java.security.NoSuchAlgorithmException;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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

	private Stage window;
	private Label user = new Label("Username");
	private Label password = new Label("Password");
	
	private TextField user_text = new TextField();
	private PasswordField password_text = new PasswordField();
	
	private Button login = new Button("Login");
	private Button newPassword = new Button("Forgot password");
	
	Image background = new Image("File:resource/login2.png");
	ImageView iv = new ImageView();
	
	
	Controller controller = Controller.getInstance();
	
	public static void main(String[] args) {
		launch(args);
	}	
	
	@Override
	public void start(Stage primaryStage) throws Exception  {
		window = primaryStage;
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
		setNodePosition((Node)user_text, 200, 100, 1.2, 1.2);
		
		pane.getChildren().add(password_text);
		setNodePosition((Node)password_text, 200, 150, 1.2, 1.2);
		
		pane.getChildren().add(login);
		setNodePosition((Node)login, 200, 200, 1.2, 1.2);
		
		pane.getChildren().add(newPassword);
		setNodePosition((Node)newPassword, 290, 200, 1.2, 1.2);
		

		login.setOnAction(e->{
			try {
				int status = controller.loginCustomer(user_text, password_text);
				if(status == 1) {
					System.out.println("Correct Password");
					// New scene
					
					//window.hide(); 				Dokonèi aby sa obnovilo predchádzajúce okno???
				}
				else if(status == -1) {
				// Missing Values - Alert Box
				System.out.println("Missing values!");
				}
			} catch (NoSuchAlgorithmException e1) {
				e1.printStackTrace();
			}
		});
		
		newPassword.setOnAction(e->{
			new NewPassword(controller);
		});
	
		Scene scene = new Scene(pane,450,300);
		window.setTitle("Login");
		window.getIcons().add(new Image("File:resource/logo.png"));
		window.setScene(scene);
		window.setResizable(false);
		window.show();
		window.setOnCloseRequest(e -> closeProgram());
		
	}		
	
	private void setNodePosition(Node node, int x, int y, double scaleX, double scaleY) {
		node.setLayoutX(x);
		node.setLayoutY(y);
		node.setScaleX(scaleX);
		node.setScaleY(scaleY);
	}

	private void closeProgram() {
		controller.shutDown();
		window.close();
	}
}
