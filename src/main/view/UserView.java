package main.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
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
	Image background = new Image("File:resource/userBack.png");
	ImageView iv = new ImageView(background);
	Color c = Color.web("#00BFFF");

	public UserView(Controller arg) {
		super();
		this.controller = arg;
	}
	
	public Scene setNewUserScene(Stage window,Scene scene,PasswordField pwd) {
		
		Pane pane = new Pane();
		pane.getChildren().add(iv);
		
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
		
		userScene = new Scene(pane, 1920, 1080);
		return userScene;
	}
	
	private void setNodePosition(Node node, int x, int y, double scaleX, double scaleY) {
		node.setLayoutX(x);
		node.setLayoutY(y);
		node.setScaleX(scaleX);
		node.setScaleY(scaleY);
	}

}
