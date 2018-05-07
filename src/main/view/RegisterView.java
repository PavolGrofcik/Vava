package main.view;

import java.util.List;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Text;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import main.controller.Controller;

public class RegisterView extends Stage {
	
	
	private Controller controller;
	private Scene registerScene ;
	private Hyperlink back = new Hyperlink();
	private Button send = new Button("Register");
	
	private Label username = new Label("Username");
	private Label password = new Label("Password");
	private Label confirmPassword = new Label("Confirm password");
	private Label question = new Label("Question");
	private Label answer = new Label("Answer");
	private Label firstName = new Label("First nane");
	private Label lastName = new Label("Last  name");
	private Label birth = new Label("Date of birth");
	private Label sex = new Label("Sex");
	private Label number = new Label("Telefon number");
	private Label city = new Label("City");
	private Label adress = new Label("Adress");
	private Label email = new Label("Email");
	private Label account = new Label("Account information");
	private Label customer = new Label("Customer information");
	
	private TextField usernameText = new TextField();
	private PasswordField passwordText = new PasswordField();
	private PasswordField confirmPasswordText = new PasswordField();
	private ComboBox<String> questionBox = new ComboBox<String>();
	private TextField answerText = new TextField();
	private TextField firstNameText = new TextField();
	private TextField lastNameText = new TextField();
	private DatePicker birthDate = new DatePicker();
	private CheckBox male = new CheckBox("Male");
	private CheckBox female = new CheckBox("Female");
	private TextField telefonText = new TextField();
	private TextField cityText = new TextField();
	private TextField adressText = new TextField();
	private TextField emailText = new TextField();



	
	Image background = new Image("File:resource/hory.jpg");
	Image backArrow = new Image("File:resource/back2.png");
	ImageView iv = new ImageView(background);
	ImageView backArrowView = new ImageView(backArrow);
	Color c = Color.web("#00BFFF");
	Color r = Color.web("#FF0000");
	Color b = Color.web("#000000");
	
	
	
	
	
	public RegisterView(Controller arg) {

		super();
		this.controller = arg;
	}
	
	public  Scene RegisterScene(Stage window,Scene scene) {
		
		Pane pane = new Pane();
		pane.getChildren().add(iv);
		
		pane.getChildren().add(account);
		setNodePosition((Node)account, 400, 100, 1, 1);
		account.setFont(Font.font(null, FontWeight.BOLD, 44));
		account.setTextFill(b);
		
		
		pane.getChildren().add(username);
		setNodePosition((Node)username, 400, 250, 1, 1);
		username.setFont(Font.font(null, FontWeight.BOLD, 20));
		username.setTextFill(c);
		
		pane.getChildren().add(usernameText);
		setNodePosition((Node)usernameText, 650, 250, 1.5, 1.5);
		
		pane.getChildren().add(password);
		setNodePosition((Node)password, 400, 330, 1, 1);
		password.setFont(Font.font(null, FontWeight.BOLD, 20));
		password.setTextFill(c);
		
		pane.getChildren().add(passwordText);
		setNodePosition((Node)passwordText, 650, 330, 1.5, 1.5);
		
		pane.getChildren().add(confirmPassword);
		setNodePosition((Node)confirmPassword, 400, 410, 1, 1);
		confirmPassword.setFont(Font.font(null, FontWeight.BOLD, 20));
		confirmPassword.setTextFill(c);
		
		pane.getChildren().add(confirmPasswordText);
		setNodePosition((Node)confirmPasswordText, 650, 410, 1.5, 1.5);
		
		pane.getChildren().add(question);
		setNodePosition((Node)question, 400, 490, 1, 1);
		question.setFont(Font.font(null, FontWeight.BOLD, 20));
		question.setTextFill(c);
		
		pane.getChildren().add(questionBox);
		setNodePosition((Node)questionBox, 650, 490, 1.5, 1.5);
		questionBox.setPrefWidth(150);
		
		pane.getChildren().add(answer);
		setNodePosition((Node)answer, 400, 570, 1, 1);
		answer.setFont(Font.font(null, FontWeight.BOLD, 20));
		answer.setTextFill(c);
		
		pane.getChildren().add(answerText);
		setNodePosition((Node)answerText, 650, 570, 1.5, 1.5);
		
		pane.getChildren().add(customer);
		setNodePosition((Node)customer, 1000, 100, 1, 1);
		customer.setFont(Font.font(null, FontWeight.BOLD, 44));
		customer.setTextFill(b);
		
		
		pane.getChildren().add(firstName);
		setNodePosition((Node)firstName, 1000, 250, 1, 1);
		firstName.setFont(Font.font(null, FontWeight.BOLD, 20));
		firstName.setTextFill(c);
		
		pane.getChildren().add(firstNameText);
		setNodePosition((Node)firstNameText, 1250, 250, 1.5, 1.5);
		
		pane.getChildren().add(lastName);
		setNodePosition((Node)lastName, 1000, 330, 1, 1);
		lastName.setFont(Font.font(null, FontWeight.BOLD, 20));
		lastName.setTextFill(c);
		
		pane.getChildren().add(lastNameText);
		setNodePosition((Node)lastNameText, 1250, 330, 1.5, 1.5);
		
		pane.getChildren().add(birth);
		setNodePosition((Node)birth, 1000, 410, 1, 1);
		birth.setFont(Font.font(null, FontWeight.BOLD, 20));
		birth.setTextFill(c);
		
		pane.getChildren().add(birthDate);
		setNodePosition((Node)birthDate, 1250, 410, 1.5, 1.5);
		birthDate.setPrefWidth(150);
		
		pane.getChildren().add(sex);
		setNodePosition((Node)sex, 1000, 490, 1, 1);
		sex.setFont(Font.font(null, FontWeight.BOLD, 20));
		sex.setTextFill(c);
		
		pane.getChildren().add(male);
		setNodePosition((Node)male, 1228, 490, 1.5, 1.5);
		male.setFont(Font.font(null, FontWeight.BOLD, 13.33));
		male.setTextFill(c);
		
		pane.getChildren().add(female);
		setNodePosition((Node)female, 1350, 490, 1.5, 1.5);
		female.setFont(Font.font(null, FontWeight.BOLD, 13.33));
		female.setTextFill(c);
		
		pane.getChildren().add(number);
		setNodePosition((Node)number, 1000, 570, 1, 1);
		number.setFont(Font.font(null, FontWeight.BOLD, 20));
		number.setTextFill(c);
		
		pane.getChildren().add(telefonText);
		setNodePosition((Node)telefonText, 1250, 570, 1.5, 1.5);
		
		pane.getChildren().add(city);
		setNodePosition((Node)city, 1000, 650, 1, 1);
		city.setFont(Font.font(null, FontWeight.BOLD, 20));
		city.setTextFill(c);
		
		pane.getChildren().add(cityText);
		setNodePosition((Node)cityText, 1250, 650, 1.5, 1.5);
		
		pane.getChildren().add(adress);
		setNodePosition((Node)adress, 1000, 730, 1, 1);
		adress.setFont(Font.font(null, FontWeight.BOLD, 20));
		adress.setTextFill(c);
		
		pane.getChildren().add(adressText);
		setNodePosition((Node)adressText, 1250, 730, 1.5, 1.5);
		
		pane.getChildren().add(email);
		setNodePosition((Node)email, 1000, 810, 1, 1);
		email.setFont(Font.font(null, FontWeight.BOLD, 20));
		email.setTextFill(c);
		
		pane.getChildren().add(emailText);
		setNodePosition((Node)emailText, 1250, 810, 1.5, 1.5);
		
		pane.getChildren().add(send);
		setNodePosition((Node)send,880,900,2,2);	
		send.setOnAction(e->{
			
			//Registration
			controller.registrateCustomer(firstNameText, lastNameText, birthDate, telefonText, cityText, emailText, adressText, female,male,
					usernameText,passwordText,confirmPasswordText,answerText,questionBox);
			
			
		});
		List<String> list = controller.getControlQuestions();
		questionBox.setValue(list.get(0));
		questionBox.getItems().addAll(list);
		
		// Only one of the checkbox can be selected at once
		male.setOnAction(e->{ 
			if(female.isSelected()) {
				female.setSelected(false);
			}else {
				male.setSelected(true);
			}
		});
		
		female.setOnAction(e->{
			if(male.isSelected()) {
				male.setSelected(false);
			}else {
				female.setSelected(true);
			}
		});
		
		pane.getChildren().add(back);
		setNodePosition((Node)back,-50,880,-0.25,0.25);
		back.setGraphic(backArrowView);
		back.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
				window.setScene(scene);
				window.show();
				window.setTitle("Forgotten password");
		    }
		});
		
		registerScene = new Scene(pane, 1920, 1080);
		return registerScene;
	}
	
	private void setNodePosition(Node node, int x, int y, double scaleX, double scaleY) {
		node.setLayoutX(x);
		node.setLayoutY(y);
		node.setScaleX(scaleX);
		node.setScaleY(scaleY);
	}
}
