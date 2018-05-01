package main.view;


import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import language.languageManager;
import main.controller.Controller;


public class LoginView extends Application {

	languageManager m = new languageManager();
	private Stage window;
	private Label user= new Label("Username");
	private Label password = new Label("Password");
	private Label welcome = new Label("Welcome to SkiGo");
	private Label error = new Label("");
	private TextField userText = new TextField();
	private PasswordField passwordText = new PasswordField();
	private Button login = new Button("Login");
	private Scene loginScene;
    Hyperlink link = new Hyperlink();
	Color c = Color.web("#00BFFF");
	Color h = Color.web("#000000");
	Color r = Color.web("#FF0000");
	Image background = new Image("File:resource/hory.jpg");
	ImageView iv = new ImageView();
	
	
	
	private Controller controller = Controller.getInstance();
	
	public static void main(String[] args) {
		launch(args);
	}	
	
	@Override
	public void start(Stage primaryStage) throws Exception  {
		window = primaryStage;
		window.setMaximized(true);
		Pane pane = new Pane();
			
		iv.setImage(background);
		pane.getChildren().add(iv);
		
		pane.getChildren().add(user);
		user.setLayoutX(800);
		user.setLayoutY(600);
		user.setFont(Font.font(null, FontWeight.BOLD, 20));
		user.setTextFill(c);
		
		pane.getChildren().add(password);
		password.setLayoutX(800);
		password.setLayoutY(650);
		password.setFont(Font.font(null, FontWeight.BOLD, 20));
		password.setTextFill(c);

		pane.getChildren().add(welcome);
		welcome.setLayoutX(300);
		welcome.setLayoutY(150);
		welcome.setTextFill(h);
		welcome.setId("header");
		
		pane.getChildren().add(error);
		error.setLayoutX(810);
		error.setLayoutY(850);
		error.setFont(Font.font(null, FontWeight.BOLD, 22));
		error.setTextFill(r);
		
		pane.getChildren().add(userText);
		setNodePosition((Node)userText, 950, 600, 1.5, 1.5);
		userText.setPromptText("Type your username");
		
		pane.getChildren().add(passwordText);
		setNodePosition((Node)passwordText, 950, 650, 1.5, 1.5);
		passwordText.setPromptText("Type your password");
		
		pane.getChildren().add(login);
		setNodePosition((Node)login, 950, 700, 1.5, 1.5);
		login.setId("login");
	
		pane.getChildren().add(link);
		setNodePosition(link, 810, 780, 1, 1);
		link.setText("Did you forget your password ?");
		link.setFont(Font.font(null, FontWeight.BOLD, 20));
		link.setTextFill(c);	
	
		loginScene = new Scene(pane,1920,1080);
		loginScene.getStylesheets().add(this.getClass().getResource("/resources/model.css").toExternalForm());
		
	
		window.setTitle("Login");
		window.getIcons().add(new Image("File:resource/logo.png"));
		window.setScene(loginScene);
		window.setResizable(false);
		window.show();
		window.setOnCloseRequest(e -> closeProgram());
			
		
		link.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		    	ForgottenPwd pwd = new ForgottenPwd(controller);
		    	error.setText("");
		    	userText.setText("");
		    	passwordText.setText("");
				window.setScene(pwd.setNewScene(window,loginScene,link));
				window.show();
				window.setTitle("Login");
		    }
		});
		
		login.setOnAction(e->{
			try {
				int status = controller.loginCustomer(userText, passwordText);
				if(status == 1) {
					System.out.println("Correct Password");
					// tu budem otvárať nové okno
				}
				else if(status == -1) {
					error.setText("Some of fields are not filled !");	
				}
				else if(status == -2) {
					error.setText("Password or username are not correct !");
				}
			} catch (NoSuchAlgorithmException e1) {
				e1.printStackTrace();
			}
		});
		
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
