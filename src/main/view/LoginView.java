package main.view;
<<<<<<< HEAD:src/main/view/MainView.java


=======
>>>>>>> 9d2a316071cde25073549147f13a240ca54a2844:src/main/view/LoginView.java
import java.security.NoSuchAlgorithmException;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
<<<<<<< HEAD:src/main/view/MainView.java
=======
import javafx.scene.paint.Color;
>>>>>>> 9d2a316071cde25073549147f13a240ca54a2844:src/main/view/LoginView.java
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import main.controller.Controller;


public class LoginView extends Application {

	private Stage window;
	private Label user = new Label("Username");
	private Label password = new Label("Password");
	private Label welcome = new Label("Welcome to SkiGo");
	private TextField userText = new TextField();
	private PasswordField passwordText = new PasswordField();
	private Button login = new Button("Login");
	private Button forgetPassword = new Button("Forgotten password");
	private Scene loginScene;
	Color c = Color.web("#FF4500");
	Color h = Color.web("#000000");
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
		
		
		pane.getChildren().add(userText);
		setNodePosition((Node)userText, 950, 600, 1.5, 1.5);
		
		pane.getChildren().add(passwordText);
		setNodePosition((Node)passwordText, 950, 650, 1.5, 1.5);
		
		pane.getChildren().add(login);
		setNodePosition((Node)login, 950, 700, 1.5, 1.5);
		login.setId("login");
		
		pane.getChildren().add(forgetPassword);
		setNodePosition((Node)forgetPassword, 920, 750, 1.5, 1.5);
		forgetPassword.setId("forget_password");
	
	
		loginScene = new Scene(pane,1920,1080);
		loginScene.getStylesheets().add(this.getClass().getResource("/resources/model.css").toExternalForm());
		
	
		window.setTitle("Login");
		window.getIcons().add(new Image("File:resource/logo.png"));
		window.setScene(loginScene);
		window.setResizable(false);
		window.show();
		window.setOnCloseRequest(e -> closeProgram());
		
		forgetPassword.setOnAction(e->{
			ForgottenPwd pwd = new ForgottenPwd(controller);
			window.setScene(pwd.setNewScene());
			window.show();
			window.setTitle("Forgotten password");
		});
		
		login.setOnAction(e->{
			try {
				int status = controller.loginCustomer(userText, passwordText);
				if(status == 1) {
					System.out.println("Correct Password");
				}
				else if(status == -1) {
				System.out.println("Missing values!");
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
