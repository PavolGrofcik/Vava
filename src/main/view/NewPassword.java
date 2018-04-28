package main.view;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.controller.Controller;

public class NewPassword extends Stage {
	
	private static final String STATUS = "Password updated!";
	
	private Button send = new Button("Send");
	private Button back = new Button("Back");
	private Label info = new Label();
	private Label name = new Label("Forgot Password");
	
	private TextField email = new TextField();
	private PasswordField answer = new PasswordField();
	private PasswordField newPassword = new PasswordField();
	private PasswordField checkNewPassword = new PasswordField();
	
	Image background = new Image("File:resource/PswdBack.jpg");
	ImageView iView  = new ImageView();
	
	private Controller controller;
	
	
	public NewPassword(Controller arg) {
		super();
		this.controller = arg;
		
		Pane pane = new Pane();
		
		iView.setImage(background);
		pane.getChildren().add(iView);
	
		initModality(Modality.APPLICATION_MODAL);
		setResizable(false);
		
		
		pane.getChildren().add(email);
		pane.getChildren().add(answer);
		pane.getChildren().add(newPassword);
		pane.getChildren().add(info);
		pane.getChildren().add(send);
		pane.getChildren().add(back);
		
		email.setPromptText("Your email");
		email.setAlignment(Pos.CENTER);
		answer.setPromptText("Your secret answer");
		answer.setAlignment(Pos.CENTER);
		newPassword.setPromptText("Your new password");
		newPassword.setAlignment(Pos.CENTER);
		
		info.setVisible(false);
		
		
		// Not working ??
		setNodeSize(email, 200, 15, 250, 100);
		setNodeSize(answer, 200, 15, 250, 150);
		setNodeSize(newPassword, 200, 15, 250, 200);
		
		
		setNodePosition((Node)send, 325, 260, 1.2, 1.2);
		setNodePosition((Node) back, 325, 520, 1.2, 1.2);
		setNodePosition(info, 325, 210, 1, 1);
		
		send.setOnAction(e->{
			// Controller method to send a new password
			int status = controller.sendNewPassword(email, answer, newPassword);
			System.out.println(status == 1 ? "Password updated" : "Invalid values");
		});
		
		back.setOnAction(e->{
			close();
		});
		
		
		getIcons().add(new Image("File:resource/logo.png"));
		setTitle("Forgot password");
		
		Scene scene = new Scene(pane, 700, 570);
		setScene(scene);
		show();
		
	}
	
	private void setNodeSize(TextField node, int width, int height, int x, int y) {
		node.setPrefSize(width, height);
		node.setLayoutX(x);
		node.setLayoutY(y);
	}
	
	private void setNodePosition(Node node, int x, int y, double scaleX, double scaleY) {
		node.setLayoutX(x);
		node.setLayoutY(y);
		node.setScaleX(scaleX);
		node.setScaleY(scaleY);
	}
}
