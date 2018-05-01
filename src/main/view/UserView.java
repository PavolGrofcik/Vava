package main.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
	private Hyperlink link = new Hyperlink("Logout");
	private Hyperlink settings = new Hyperlink();
	private Label settingText = new Label("Settings");
	private PasswordField oldPassword = new PasswordField();
	private PasswordField newPassword = new PasswordField();
	private PasswordField repeatNewPassword = new PasswordField();
	private Label oldPasswordLabel = new Label("Password");
	private Label newPasswordLabel = new Label("New password");
	private Label repeatNewPasswordLabel = new Label("New password");
	private Button changePassword = new Button("Change");

	Image background = new Image("File:resource/userBack.png");
	ImageView iv = new ImageView(background);
	Image settingWheel = new Image("File:resource/userSetting.png");
	ImageView settingView = new ImageView(settingWheel);
	Image settingsBackground = new Image("File:resource/wood.png");
	ImageView settingsBackgroundView = new ImageView(settingsBackground);
	Color c = Color.web("#000000");

	public UserView(Controller arg) {
		super();
		this.controller = arg;
	}
	
	public Scene setNewUserScene(Stage window,Scene scene,PasswordField pwd) {
		
		Pane pane = new Pane();
		pane.getChildren().add(iv);
		
		Pane settingsPane = new Pane();
		settingsPane.setPrefSize(500,400);
		settingsPane.setId("setting");
		pane.getChildren().add(settingsPane);
		setNodePosition((Node)settingsPane, 800, 350, 1, 1);
		settingsPane.setVisible(false);
		
		settingsPane.getChildren().add(oldPassword);
		setNodePosition((Node)oldPassword, 260, 50, 1.5, 1.5);
		oldPassword.setPromptText("Type old password");
		
		settingsPane.getChildren().add(newPassword);
		setNodePosition((Node)newPassword, 260, 130, 1.5, 1.5);
		newPassword.setPromptText("Type new password");
		
		settingsPane.getChildren().add(repeatNewPassword);
		setNodePosition((Node)repeatNewPassword, 260, 210, 1.5, 1.5);
		repeatNewPassword.setPromptText("Repeat new password");
		
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
		
		settingsPane.getChildren().add(changePassword);
		setNodePosition((Node)changePassword, 250, 260, 1.5, 1.5);
		changePassword.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
			  // tu rob co ches
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
	
	private void setNodePosition(Node node, int x, int y, double scaleX, double scaleY) {
		node.setLayoutX(x);
		node.setLayoutY(y);
		node.setScaleX(scaleX);
		node.setScaleY(scaleY);
	}

}
