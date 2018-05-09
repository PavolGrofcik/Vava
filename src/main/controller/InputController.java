package main.controller;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

/**
 * Trieda zodpovedná za overenie integrity vstupov od používateľa,
 * ktorý zadá dané vstupy na vykonanie operácií, vyžadujúcich prístup
 * s DB.
 * @author grofc
 *
 */

public class InputController {

	
	// Metóda overí vstupy pri registrácií zákazníka - customer
	public static int verifyCustomerInput(TextField name, TextField surname, DatePicker birth, CheckBox male,
			CheckBox female, TextField telNumber, TextField city, TextField email, TextField address) {

		if(name.getText().isEmpty() || surname.getText().isEmpty() || birth.getValue() == null
				|| (!male.isSelected() && !female.isSelected()) || telNumber.getText().isEmpty() 
				|| city.getText().isEmpty() || email.getText().isEmpty() || address.getText().isEmpty()) {
			return 0;
		}
		
		return 1;
	}
	
	// Metóda overí vstupy pri registrácií zákazníka - account
	public static int verifyAccountInput(TextField username, PasswordField password, PasswordField confirm,
			TextField answer, ComboBox<String> question) {
		if(username.getText().isEmpty() || password.getText().isEmpty() || confirm.getText().isEmpty() 
				|| answer.getText().isEmpty() || question.getValue().isEmpty()) {
			return 0;
		}
			
		return 1;
	}
	
	// Metóda overí vstupy pri filtrovaní Tableview
	public static int verifyFilterInput(TextField location, DatePicker date, Spinner<Integer> length,
			Spinner<Integer> price) {
		if(location.getText().isEmpty() && date.getValue() == null && length.getValue().toString().isEmpty()
				&& price.getValue().toString().isEmpty()) {
			return 0;
		}
		
		return 1;
	}
	
}
