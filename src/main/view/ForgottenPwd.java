package main.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
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

public class ForgottenPwd extends Stage {
	
	
	private Controller controller;
	private Scene forgotten_pwd_scene ;
	private Label username = new Label("Username");
	private Label controlQuestion = new Label("Question");
	private Label answer = new Label("Answer");
	private Label error = new Label("");
	private Hyperlink back = new Hyperlink();
	private TextField questionText = new TextField();
	private TextField answerText = new TextField();
	private TextField usernameText = new TextField();
	private Button send = new Button("Send new password");
	Image background = new Image("File:resource/hory.jpg");
	Image backArrow = new Image("File:resource/back2.png");
	ImageView iv = new ImageView(background);
	ImageView backArrowView = new ImageView(backArrow);
	Color c = Color.web("#00BFFF");
	Color r = Color.web("#FF0000");
	
	
	public ForgottenPwd(Controller arg) {
		super();
		this.controller = arg;
	}
	
	public  Scene setNewScene(Stage window,Scene scene,Hyperlink link) {
		
		Pane pane = new Pane();
		pane.getChildren().add(iv);
		
		pane.getChildren().add(username);		
		setNodePosition(username, 800, 420, 1, 1);
		username.setFont(Font.font(null, FontWeight.BOLD, 20));
		username.setTextFill(c);
	
		
		pane.getChildren().add(controlQuestion);
		setNodePosition(controlQuestion, 800, 500, 1, 1);
		controlQuestion.setFont(Font.font(null, FontWeight.BOLD, 20));
		controlQuestion.setTextFill(c);
		
		pane.getChildren().add(answer);
		setNodePosition(answer, 800, 580, 1, 1);
		answer.setFont(Font.font(null, FontWeight.BOLD, 20));
		answer.setTextFill(c);
		
		pane.getChildren().add(error);
		setNodePosition(error, 850, 800, 1, 1);
		error.setFont(Font.font(null, FontWeight.BOLD, 20));
		error.setTextFill(r);
		
		pane.getChildren().add(usernameText);		
		setNodePosition(usernameText, 1000, 420, 1.5, 1.5);
		
		pane.getChildren().add(questionText);
		setNodePosition((Node)questionText, 1000, 500, 1.5, 1.5);
		questionText.setEditable(false);
		
		pane.getChildren().add(answerText);
		setNodePosition((Node)answerText, 1000, 580, 1.5, 1.5);
		
		pane.getChildren().add(send);
		setNodePosition((Node)send,940,660,1.5,1.5);
		

		pane.getChildren().add(back);
		setNodePosition((Node)back,-50,880,-0.25,0.25);
		back.setGraphic(backArrowView);
		
		usernameText.setOnAction(e->{
			String tmp =controller.getQuestionByUsername(usernameText);
			if(tmp.isEmpty()) {
				error.setText("Username is not correct !");
			}else {
				System.out.println(tmp);
				questionText.setText(tmp);
			}
			
		});

	
		send.setOnAction(e->{
			int a = controller.sendNewPassword(usernameText, answerText, questionText);
			System.out.println(a);
		});
			
		

		back.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
				window.setScene(scene);
				window.show();
				window.setTitle("Forgotten password");
				link.setVisited(false);
				link.setBorder(Border.EMPTY);
		    }
		});
		
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
