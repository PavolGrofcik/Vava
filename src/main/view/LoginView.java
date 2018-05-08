package main.view;


import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.application.Platform;
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
	private Label welcome;
	private Label error = new Label("");
	private TextField userText = new TextField();
	private PasswordField passwordText = new PasswordField();
	private Button login;
	private Button register;
	private Hyperlink slovakFlag = new Hyperlink();
	private Hyperlink englandFlag = new Hyperlink();
	private Scene loginScene;
    private Hyperlink link = new Hyperlink();
	Color c = Color.web("#00BFFF");
	Color h = Color.web("#000000");
	Color r = Color.web("#FF0000");
	Image background = new Image("File:resource/hory.jpg");
	Image slovakFlagImage = new Image("File:resource/slovak.png");
	Image englandFlagImage = new Image("File:resource/england.png");
	ImageView iv = new ImageView();
	ImageView ivSlovak = new ImageView(slovakFlagImage);
	ImageView ivEngland = new ImageView(englandFlagImage);
	ResourceBundle resource ;
	
	
	
	private Controller controller = Controller.getInstance();
	
	public static void main(String[] args) {
		launch(args);
	}	
	
	@Override
	public void start(Stage primaryStage) throws Exception  { 
		resource=m.englishLanguage();
		welcome = new Label(resource.getString("key3"));
		login = new Button(resource.getString("key4"));
		register = new Button(resource.getString("key5"));
		userText.setPromptText("Type your name");
    	passwordText.setPromptText("Type your password");
		
		window = primaryStage;
		window.setMaximized(true);
		Pane pane = new Pane();
			
		iv.setImage(background);
		pane.getChildren().add(iv);

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
		setNodePosition((Node)userText, 870, 600, 1.5, 1.5);
		userText.setPrefWidth(200);
		
		pane.getChildren().add(passwordText);
		setNodePosition((Node)passwordText, 870, 650, 1.5, 1.5);
		passwordText.setPrefWidth(200);
		
		pane.getChildren().add(login);
		setNodePosition((Node)login,920, 700, 1.5, 1.5);
		login.setId("login");
		login.setPrefWidth(100);
	
		pane.getChildren().add(register);
		setNodePosition((Node)register, 1750, 1000, 1.5, 1.5);
		register.setId("register");
		register.setPrefWidth(100);
		
		pane.getChildren().add(slovakFlag);
		setNodePosition((Node)slovakFlag, 1650,-50, 0.2 ,0.2);
		slovakFlag.setGraphic(ivSlovak);
		
		pane.getChildren().add(englandFlag);
		setNodePosition((Node)englandFlag, 1800, 10, 0.7 ,0.7);
		englandFlag.setGraphic(ivEngland);
		
		pane.getChildren().add(link);
		setNodePosition(link, 820, 780, 1, 1);
		link.setText(resource.getString("key6"));
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
		pane.requestFocus();
			
		register.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		    	RegisterView w = new RegisterView(controller);
		    	error.setText("");
		    	userText.setText("");
		    	passwordText.setText("");
				window.setScene(w.RegisterScene(window,loginScene,resource));
				window.show();
				window.setTitle(resource.getString("key11"));
		    }
		});
		
		link.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		    	ForgottenPwd pwd = new ForgottenPwd(controller);
		    	error.setText("");
		    	userText.setText("");
		    	passwordText.setText("");
				window.setScene(pwd.setNewScene(window,loginScene,link,resource));
				window.show();
				window.setTitle(resource.getString("key9"));
		    }
		});
		
		slovakFlag.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		    	welcome.setLayoutX(400);
				welcome.setLayoutY(150);
		    	resource=m.slovakLanguage();
		    	setNodePosition(link, 880, 780, 1, 1);
		    	userText.setPromptText("Zadajte meno");
		    	passwordText.setPromptText("Zadajte heslo");
				welcome.setText("Vitajte v SkiGo");
				login.setText("Prihlásenie");
				register.setText("Registrácia");
				link.setText("Zabudli ste heslo?");
				window.setTitle("Prihlasovanie");
		    }
		});
		englandFlag.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		    	welcome.setLayoutX(300);
				welcome.setLayoutY(150);
		    	resource=m.englishLanguage();
		    	userText.setPromptText("Type your name");
		    	passwordText.setPromptText("Type your password");
				welcome.setText("WELCOME TO SKIGO");
				login.setText("Login");
				register.setText("Register");
				setNodePosition(link, 820, 780, 1, 1);
				link.setText("Did you forget your password?");
				window.setTitle("Login");
		    }
		});
		
		login.setOnAction(e->{
			try {
				int status = controller.loginCustomer(userText, passwordText);
				if(status == 1) {
					UserView u = new UserView(controller);
					window.setScene(u.setNewUserScene(window,loginScene,passwordText,resource));
					window.setTitle(resource.getString("key10"));
					window.show();
				}
				else if(status == -1) {
					error.setText(resource.getString("key7"));	
				}
				else if(status == -2) {
					error.setText(resource.getString("key8"));
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
