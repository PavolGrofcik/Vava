package main.view;

import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import main.controller.Controller;
import main.entities.Event;

public class UserView extends Stage {
	
	private Controller controller;
	private Scene userScene ;
	private Hyperlink link;
	private Hyperlink settings = new Hyperlink();
	private Label settingText;
	private PasswordField oldPassword = new PasswordField();
	private PasswordField newPassword = new PasswordField();
	private PasswordField repeatNewPassword = new PasswordField();
	private TextField newTelefon= new TextField();
	private TextField newEmail= new TextField();
	
	private Label oldPasswordLabel;
	private Label newPasswordLabel;
	private Label repeatNewPasswordLabel;
	private Label newTelefonLabel;
	private Label newEmailLabel;
	private Button change;
	private Label errorSettings = new Label("");

	// dashboard

	private Label loggedUser;
	private Label loggedUserName = new Label("");
	private Label balance;
	private Label balanceText= new Label();
	private Button addBalanceWindow;
	private TextField sum = new TextField();
	private Button addBalance;

	//table
	
	private Hyperlink showUpcomingEvents;
	private TableView<Event> eventsTable = new TableView<Event>();
	private ArrayList<Event> eventList = new ArrayList<Event>();
	private Button find = new Button();
	private TextField locationnFilter = new TextField();
	private DatePicker dateFilter = new DatePicker();
	private Spinner<Integer> lengthFilter = new Spinner<Integer>(0,2000,0,1);
	private Spinner<Integer> priceFilter = new Spinner<Integer>(0,2000,0,1);
	private Label locationLabel;
	private Label dateLabel;
	private Label lengthLabel;
	private Label priceLabel;
	private Button registerForEvent = new Button();
	private Hyperlink exit = new Hyperlink();
	private CheckBox accept = new CheckBox();
	Integer tmp=null;
	
 

	// praca s farbou a obrazkami
	Image background = new Image("File:resource/userBack.png");
	ImageView iv = new ImageView(background);
	Image settingWheel = new Image("File:resource/userSetting.png");
	ImageView settingView = new ImageView(settingWheel);
	Image settingsBackground = new Image("File:resource/wood.png");
	ImageView settingsBackgroundView = new ImageView(settingsBackground);
	Image exitImage = new Image("File:resource/exit.png");
	ImageView exitView = new ImageView(exitImage);
	Color c = Color.web("#000000");
	Color r = Color.web("#FF0000");

	public UserView(Controller arg) {
		super();
		this.controller = arg;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Scene setNewUserScene(Stage window,Scene scene,PasswordField pwd,ResourceBundle resource) {
		
		final WebView  browser = new WebView();
		final WebEngine engine = browser.getEngine();
		
		
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
		find.setText(resource.getString("key1-24"));
		showUpcomingEvents = new Hyperlink(resource.getString("key1-17"));
		TableColumn location = new TableColumn(resource.getString("key1-18"));
	    TableColumn date = new TableColumn(resource.getString("key1-19"));
	    TableColumn length = new TableColumn(resource.getString("key1-20"));
	    TableColumn height = new TableColumn(resource.getString("key1-21"));
        TableColumn insurance = new TableColumn(resource.getString("key1-22"));
        TableColumn price = new TableColumn(resource.getString("key1-23"));
        locationLabel = new Label(resource.getString("key1-25"));
    	dateLabel = new Label(resource.getString("key1-26"));
    	lengthLabel = new Label(resource.getString("key1-27"));
    	priceLabel = new Label(resource.getString("key1-28"));

        location.setPrefWidth(200);
        date.setPrefWidth(200);
        length.setPrefWidth(90);
        height.setPrefWidth(90);
        insurance.setPrefWidth(90);
        price.setPrefWidth(85);
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        date.setCellValueFactory(new PropertyValueFactory<>("start"));
        length.setCellValueFactory(new PropertyValueFactory<>("length"));
        height.setCellValueFactory(new PropertyValueFactory<>("height"));
        insurance.setCellValueFactory(new PropertyValueFactory<>("insurance"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        location.setResizable(false);
        date.setResizable(false);
        length.setResizable(false);
        height.setResizable(false);
        insurance.setResizable(false);
        price.setResizable(false);
        registerForEvent.setText(resource.getString("key1-29"));
        accept.setText(resource.getString("key1-30"));
        exit.setGraphic(exitView);
        
       /* eventList = controller.getEventList(locationnFilter, dateFilter, lengthFilter,
        		priceFilter);*/
        eventList = null;
	
		Pane pane = new Pane();
		Pane settingsPane = new Pane();
		Pane addBalacePane = new Pane();
		Pane eventsPane = new Pane();
		Pane registerOnEventPane = new Pane();
		
	
		pane.getChildren().add(iv);
		
		
		// miesto pre balanc a ucet
		
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
				  showUpcomingEvents.setVisible(true);
			  }
			  else {
				  addBalacePane.setVisible(true);
				  showUpcomingEvents.setVisible(false);
			  }
		    }
		});	


		addBalance.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		    	controller.setUserBalance(sum);
		    	balanceText.setText(controller.getUserBalance());
		    }
		});

		// miesto pre eventy
		
		
		eventsPane.setPrefSize(800,500);
		eventsPane.setId("funds");
		pane.getChildren().add(eventsPane);
		setNodePosition((Node)eventsPane, 20, 260, 1, 1);
		eventsPane.setVisible(false);

		pane.getChildren().add(showUpcomingEvents);
		setNodePosition((Node)showUpcomingEvents,20, 200, 1, 1);
		showUpcomingEvents.setFont(Font.font(null, FontWeight.BOLD, 20));
		showUpcomingEvents.setTextFill(c);
		showUpcomingEvents.setBorder(Border.EMPTY);
		
		eventsPane.getChildren().add(find);
		setNodePosition((Node)find,380, 460, 1.5, 1.5);
		
		eventsPane.getChildren().add(locationnFilter);
		setNodePosition((Node)locationnFilter,60, 60, 1.5, 1.5);
		locationnFilter.setPrefWidth(100);
		
		eventsPane.getChildren().add(dateFilter);
		setNodePosition((Node)dateFilter,250, 60, 1.5, 1.5);
		dateFilter.setPrefWidth(100);
		
		eventsPane.getChildren().add(lengthFilter);
		setNodePosition((Node)lengthFilter,440, 60, 1.5, 1.5);
		lengthFilter.setPrefWidth(100);
		
		eventsPane.getChildren().add(priceFilter);
		setNodePosition((Node)priceFilter,630, 60, 1.5, 1.5);
		priceFilter.setPrefWidth(100);
		
		eventsPane.getChildren().add(locationLabel);
		setNodePosition((Node)locationLabel, 60, 20, 1, 1);
		locationLabel.setFont(Font.font(null, FontWeight.BOLD, 20));
		locationLabel.setTextFill(c);
		
		eventsPane.getChildren().add(dateLabel);
		setNodePosition((Node)dateLabel, 250, 20, 1, 1);
		dateLabel.setFont(Font.font(null, FontWeight.BOLD, 20));
		dateLabel.setTextFill(c);
		
		eventsPane.getChildren().add(lengthLabel);
		setNodePosition((Node)lengthLabel, 440, 20, 1, 1);
		lengthLabel.setFont(Font.font(null, FontWeight.BOLD, 20));
		lengthLabel.setTextFill(c);
		
		eventsPane.getChildren().add(priceLabel);
		setNodePosition((Node)priceLabel, 630, 20, 1, 1);
		priceLabel.setFont(Font.font(null, FontWeight.BOLD, 20));
		priceLabel.setTextFill(c);
		
		find.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		    	eventList = controller.getEventList(locationnFilter, dateFilter, lengthFilter,
		        		priceFilter);
				if (eventList == null) {
					System.out.println("Empty list");
				} else {
					eventsTable.getItems().clear();
					for (int i = 0; i < eventList.size(); i++) {
						eventsTable.getItems().add(eventList.get(i));
					}
				}
			 }
		});
		
		eventsPane.getChildren().add(eventsTable);
		setNodePosition((Node)eventsTable,20, 100, 1, 1);
		eventsTable.setPrefSize(760,350);
		eventsTable.getColumns().addAll(location,date,length,height,insurance,price);
		eventsTable.setEditable(false);
	
		
		
		showUpcomingEvents.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
			  if(eventsPane.isVisible())
				  eventsPane.setVisible(false);
			  else 
				  eventsPane.setVisible(true);
			 }
		});
		
		registerOnEventPane.getChildren().add(exit);
		setNodePosition((Node)exit,1430, 630, 0.2, 0.2);
		exit.setBorder(Border.EMPTY);
		
		registerOnEventPane.getChildren().add(registerForEvent);
		setNodePosition((Node)registerForEvent,780, 750, 1.5, 1.5);
		registerForEvent.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		    	int status = controller.registerToEvent(tmp,accept);
		    	switch(status) {
		    	case 1:break;
		    	case-1:break;
		    	case-2:break;
		    	}
			 }
		});
		
		registerOnEventPane.getChildren().add(accept);
		setNodePosition((Node)accept, 100, 750, 1, 1);
		accept.setFont(Font.font(null, FontWeight.BOLD, 20));
		accept.setTextFill(c);

		exit.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent e) {
		    	registerOnEventPane.setVisible(false);
			 }
		});
		
		registerOnEventPane.setPrefSize(1600,800);
		registerOnEventPane.setId("setting");
		pane.getChildren().add(registerOnEventPane);
		setNodePosition((Node)registerOnEventPane, 200, 100, 1, 1);
		registerOnEventPane.setVisible(false);
		
		registerOnEventPane.getChildren().add(browser);
		setNodePosition((Node)browser,20,20,1,1);
		browser.setPrefSize(1560,700);
		
		eventsTable.setRowFactory( tv -> {
		    TableRow<Event> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		            int rowData = row.getItem().getId();
<<<<<<< HEAD
=======
<<<<<<< HEAD
		            tmp = rowData;
		            engine.load(controller.getEventUrl(rowData));
=======
<<<<<<< HEAD
>>>>>>> 4e66f5f34dc022f511dc6e92b0cb9ba6580ddc95
		            engine.load(controller.getEventUrl(rowData));
		            String url = controller.getEventUrl(rowData);
<<<<<<< HEAD

=======
		            System.out.println(url);
>>>>>>> 2c95cd562492afb9bbc08cce2fc9af3a493e5426
>>>>>>> 45b9bf2bb86ea6aff4dce34cd1c8dc0794d2a71b
>>>>>>> 4e66f5f34dc022f511dc6e92b0cb9ba6580ddc95
		            addBalacePane.setVisible(false);
		            eventsPane.setVisible(false);
		            settingsPane.setVisible(false);
		            registerOnEventPane.setVisible(true);
		        }
		    });
		    return row ;
		});	
		
		
		
		// miesto pre settingpane
		
		
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
