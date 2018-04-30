package main.view;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import main.controller.Controller;

public class ForgottenPwd extends Stage {
	
	
	private Controller controller;
	static Scene forgotten_pwd_scene ;
	private Label username = new Label("Username");
	private Label controlQestion = new Label("Question");
	private Label answer = new Label("Answer");
	private TextField questionText = new TextField();
	private TextField answerText = new TextField();
	private TextField usernameText = new TextField();
	private Button send = new Button("Send new password");
	Image background = new Image("File:resource/hory.jpg");
	ImageView iv = new ImageView();
	Color c = Color.web("#FF4500");
	
	
	
	public ForgottenPwd(Controller arg) {
		//super();
		this.controller = arg;
	}
	
	public  Scene setNewScene() {
		
		Pane pane = new Pane();
		iv.setImage(background);
		pane.getChildren().add(iv);
		
		pane.getChildren().add(username);		
		setNodePosition(username, 800, 420, 1, 1);
		username.setFont(Font.font(null, FontWeight.BOLD, 20));
		username.setTextFill(c);
	
		
		pane.getChildren().add(controlQestion);
		setNodePosition(controlQestion, 800, 500, 1, 1);
		controlQestion.setFont(Font.font(null, FontWeight.BOLD, 20));
		controlQestion.setTextFill(c);
		
		pane.getChildren().add(answer);
		setNodePosition(answer, 800, 580, 1, 1);
		answer.setFont(Font.font(null, FontWeight.BOLD, 20));
		answer.setTextFill(c);
		
		pane.getChildren().add(usernameText);		
		setNodePosition(usernameText, 1000, 420, 1.5, 1.5);
		
		pane.getChildren().add(questionText);
		setNodePosition((Node)questionText, 1000, 500, 1.5, 1.5);
		
		pane.getChildren().add(answerText);
		setNodePosition((Node)answerText, 1000, 580, 1.5, 1.5);
		
		pane.getChildren().add(send);
		setNodePosition((Node)send,940,660,1.5,1.5);
		
	
		
		
		
		
		
		forgotten_pwd_scene = new Scene(pane, 1920, 1080);
		return forgotten_pwd_scene;
	}
	
	private void setNodePosition(Node node, int x, int y, double scaleX, double scaleY) {
		node.setLayoutX(x);
		node.setLayoutY(y);
		node.setScaleX(scaleX);
		node.setScaleY(scaleY);
	}
}
