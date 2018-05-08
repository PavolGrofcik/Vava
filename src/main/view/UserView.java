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
	private Label oldPasswordLabel;
	private Label newPasswordLabel;
	private Label repeatNewPasswordLabel;
	private Label newTelefonLabel;
	private Label newEmailLabel;
	private Button change;

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
	
	public Scene setNewUserScene(Stage window,Scene scene,PasswordField pwd,ResourceBundle resource) {
		
		link = new Hyperlink(resource.getString("key1-1"));
		settingText = new Label(resource.getString("key1-2"));
	    oldPasswordLabel = new Label(resource.getString("key1-3"));
		newPasswordLabel = new Label(resource.getString("key1-4"));
		repeatNewPasswordLabel = new Label(resource.getString("key1-5"));
		newTelefonLabel = new Label(resource.getString("key1-6"));
		newEmailLabel = new Label(resource.getString("key1-7"));
		change = new Button(resource.getString("key1-8"));
		
		
		Pane pane = new Pane();
		pane.getChildren().add(iv);
		
		
		Pane settingsPane = new Pane();
		settingsPane.setPrefSize(500,400);
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
		
		settingsPane.getChildren().add(change);
		setNodePosition((Node)change, 240, 450, 1.5, 1.5);
		change.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
			  int status = controller.changeAccountSettings(oldPassword, newPassword, repeatNewPassword, newTelefon, newEmail);
			  System.out.println("Status is " + status);
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
	
	private void setNodePosition(Node node, int x, int y, double scaleX, double scaleY ) {
		node.setLayoutX(x);
		node.setLayoutY(y);
		node.setScaleX(scaleX);
		node.setScaleY(scaleY);
	}

}
