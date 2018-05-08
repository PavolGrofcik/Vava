package main.view;

import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import main.controller.Controller;

public class UserView extends Stage {
	
	private Controller controller;
	private Scene userScene ;
	private Hyperlink link;
	private Hyperlink settings = new Hyperlink();
	private Label settingText;
	private PasswordField oldPassword = new PasswordField();
	private PasswordField newPassword = new PasswordField();
	private PasswordField repeatNewPassword = new PasswordField();

	private TextField newTelefon = new TextField();
	private TextField newEmail = new TextField();
	private Label oldPasswordLabel = new Label("Password");
	private Label newPasswordLabel = new Label("New password");
	private Label repeatNewPasswordLabel = new Label("New password");
	private Label newTelefonLabel = new Label("New number");
	private Label newEmailLabel = new Label("New email");
	private Button change = new Button("Change");
	private Label errorSettings = new Label("");


	// dashboard

	private Label loggedUser;
	private Label loggedUserName = new Label("");
	private Label balance;
	private Label balanceText= new Label();
	private Button addBalanceWindow;
	private TextField sum = new TextField();
	private Button addBalance;

	Image background = new Image("File:resource/userBack.png");
	ImageView iv = new ImageView(background);
	Image settingWheel = new Image("File:resource/userSetting.png");
	ImageView settingView = new ImageView(settingWheel);
	Image settingsBackground = new Image("File:resource/wood.png");
	ImageView settingsBackgroundView = new ImageView(settingsBackground);
	Color c = Color.web("#000000");
	Color r = Color.web("#FF0000");

	public UserView(Controller arg) {
		super();
		this.controller = arg;
	}
	
	public Scene setNewUserScene(Stage window,Scene scene,PasswordField pwd,ResourceBundle resource) {
		
		link = new Hyperlink(resource.getString("key1-1"));
		settingText = new Label(resource.getString("key1-2"));
	    oldPasswordLabel = new Label(resource.getString("key1-3"));
		newPasswordLabel = new Label(resource.getString("key1-4"));
		repeatNewPasswordLabel = new Label(resource.getString("key1-5"));
		newTelefonLabel = new Label(resource.getString("key1-6"));
		newEmailLabel = new Label(resource.getString("key1-7"));
		change = new Button(resource.getString("key1-8"));
		loggedUser= new Label(resource.getString("key1-9"));
		balance = new Label(resource.getString("key1-10"));
		addBalanceWindow = new Button(resource.getString("key1-11"));
		addBalance = new Button(resource.getString("key1-12"));
	
		Pane pane = new Pane();
		pane.getChildren().add(iv);
		
		pane.getChildren().add(loggedUser);
		setNodePosition((Node)loggedUser, 20, 20, 1, 1);
		loggedUser.setFont(Font.font(null, FontWeight.BOLD, 20));
		loggedUser.setTextFill(c);
		
		pane.getChildren().add(loggedUserName);
		setNodePosition((Node)loggedUserName, 110, 20, 1, 1);
		loggedUserName.setFont(Font.font(null, FontWeight.BOLD, 20));
		loggedUserName.setTextFill(c);
		loggedUserName.setText(controller.getUser());
		
		pane.getChildren().add(balance);
		setNodePosition((Node)balance, 20, 60, 1, 1);
		balance.setFont(Font.font(null, FontWeight.BOLD, 20));
		balance.setTextFill(c);
		
		pane.getChildren().add(balanceText);
		setNodePosition((Node)balanceText, 110, 60, 1, 1);
		balanceText.setFont(Font.font(null, FontWeight.BOLD, 20));
		balanceText.setTextFill(c);
		balanceText.setText(controller.getUserBalance());
		
		
		Pane addBalacePane = new Pane();
		pane.getChildren().add(addBalacePane);
		setNodePosition((Node)addBalacePane, 10, 100, 1, 1);
		addBalacePane.setId("funds");
		addBalacePane.setPrefSize(220,120);
		addBalacePane.setVisible(false);
		
		addBalacePane.getChildren().add(sum);
		setNodePosition((Node)sum,50, 20 , 1.5, 1.5);
		sum.setPrefWidth(120);
		
		addBalacePane.getChildren().add(addBalance);
		setNodePosition((Node)addBalance,80, 70 , 1.2, 1.2);
		addBalance.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
			   controller.setUserBalance(sum);
			   balanceText.setText(controller.getUserBalance());
		    }
		});
		
		pane.getChildren().add(addBalanceWindow);
		setNodePosition((Node)addBalanceWindow, 180, 60, 1.2, 1.2);
		addBalanceWindow.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
			  sum.setText("");
		    	if(addBalacePane.isVisible()) {
				  addBalacePane.setVisible(false);
			  }
			  else
				  addBalacePane.setVisible(true);
		    }

		});
		
		addBalance.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		    	controller.setUserBalance(sum);
		    	balanceText.setText(controller.getUserBalance());
		    }
		});

		
		Pane settingsPane = new Pane();
		settingsPane.setPrefSize(490,560);
		settingsPane.setId("setting");
		pane.getChildren().add(settingsPane);
		setNodePosition((Node)settingsPane, 750, 300, 1, 1);
		settingsPane.setVisible(false);
		
		settingsPane.getChildren().add(oldPassword);
		setNodePosition((Node)oldPassword, 260, 50, 1.5, 1.5);
		oldPassword.setPrefWidth(150);
		
		settingsPane.getChildren().add(newPassword);
		setNodePosition((Node)newPassword, 260, 130, 1.5, 1.5);
		newPassword.setPrefWidth(150);
		
		settingsPane.getChildren().add(repeatNewPassword);
		setNodePosition((Node)repeatNewPassword, 260, 210, 1.5, 1.5);
		repeatNewPassword.setPrefWidth(150);
		
		settingsPane.getChildren().add(oldPasswordLabel);
		setNodePosition((Node)oldPasswordLabel, 40, 50, 1, 1);
		oldPasswordLabel.setFont(Font.font(null, FontWeight.BOLD, 20));
		oldPasswordLabel.setTextFill(c);
	
		
		settingsPane.getChildren().add(newPasswordLabel);
		setNodePosition((Node)newPasswordLabel, 40, 130, 1, 1);
		newPasswordLabel.setFont(Font.font(null, FontWeight.BOLD, 20));
		newPasswordLabel.setTextFill(c);
		
		settingsPane.getChildren().add(repeatNewPasswordLabel);
		setNodePosition((Node)repeatNewPasswordLabel, 40, 210, 1, 1);
		repeatNewPasswordLabel.setFont(Font.font(null, FontWeight.BOLD, 20));
		repeatNewPasswordLabel.setTextFill(c);
		
		
		settingsPane.getChildren().add(newTelefonLabel);
		setNodePosition((Node)newTelefonLabel, 40, 290, 1, 1);
		newTelefonLabel.setFont(Font.font(null, FontWeight.BOLD, 20));
		newTelefonLabel.setTextFill(c);
		
		settingsPane.getChildren().add(newEmailLabel);
		setNodePosition((Node)newEmailLabel, 40, 370, 1, 1);
		newEmailLabel.setFont(Font.font(null, FontWeight.BOLD, 20));
		newEmailLabel.setTextFill(c);
		
		settingsPane.getChildren().add(newTelefon);
		setNodePosition((Node)newTelefon, 260, 290, 1.5, 1.5);
		newTelefon.setPrefWidth(150);
		
		settingsPane.getChildren().add(newEmail);
		setNodePosition((Node)newEmail, 260, 370, 1.5, 1.5);
		newEmail.setPrefWidth(150);
		
		settingsPane.getChildren().add(errorSettings);
		setNodePosition((Node)errorSettings, 140, 500, 1, 1);
		errorSettings.setFont(Font.font(null, FontWeight.BOLD, 20));
		errorSettings.setTextFill(r);
		
		
		settingsPane.getChildren().add(change);
		setNodePosition((Node)change, 240, 450, 1.5, 1.5);
		change.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
			  int status = controller.changeAccountSettings(oldPassword, newPassword, repeatNewPassword, newTelefon, newEmail);
			  switch(status) {
			  case -1: errorSettings.setText(resource.getString("key1-13"));break;
			  case -2: errorSettings.setText(resource.getString("key1-14"));break;
			  case -3: errorSettings.setText(resource.getString("key1-15"));break;
			  case 1: errorSettings.setText(resource.getString("key1-16"));break; 
			  }
			  
		    }
		});
		
		
		
		
		pane.getChildren().add(settingText);
		setNodePosition((Node)settingText, 1800, 60, 1, 1);
		settingText.setFont(Font.font(null, FontWeight.BOLD, 20));
		settingText.setTextFill(c);
		
		pane.getChildren().add(link);
		setNodePosition(link, 1800, 1000, 1, 1);
		link.setBorder(Border.EMPTY);
		link.setFont(Font.font(null, FontWeight.BOLD, 20));
		link.setTextFill(c);
		link.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
				window.setScene(scene);
				window.show();
				window.setTitle("Login");
				pwd.setText("");
		    }
		});
		
		pane.getChildren().add(settings);
		setNodePosition(settings, 1360,-450, 0.05, 0.05);
		settings.setBorder(Border.EMPTY);
		settings.setGraphic(settingView);
		settings.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		    	errorSettings.setText("");
				if(settingsPane.isVisible())
					settingsPane.setVisible(false);
		    	else
		    		settingsPane.setVisible(true);
		    }
		});
		
		
		
		userScene = new Scene(pane, 1920, 1080);
		userScene.getStylesheets().add(this.getClass().getResource("/resources/model.css").toExternalForm());
		return userScene;
	}
	
	private void setNodePosition(Node node, int x, int y, double scaleX, double scaleY ) {
		node.setLayoutX(x);
		node.setLayoutY(y);
		node.setScaleX(scaleX);
		node.setScaleY(scaleY);
	}

}
