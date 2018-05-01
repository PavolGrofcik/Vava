package main.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="account")
public class Account {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="customer_id")		//One to one relationship
	private int customerId;
	
	@Column(name="control_question_id")			//One to many relationship
	private int controlQuestionId;
	
	@Column(name="cash")
	private double cash;
	
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="answer")
	private String answer;
	


	public Account(int id, int controlQuestionId, String username, String answer) {
		super();
		this.id = id;
		this.controlQuestionId = controlQuestionId;
		this.username = username;
		this.answer = answer;
	}
	
	

	public Account(int id, int customerId, int controlQuestionId, String username, String password, String answer) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.controlQuestionId = controlQuestionId;
		this.username = username;
		this.password = password;
		this.answer = answer;
	}



	public Account(int id, int customerId, String username, String password, String answer) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.username = username;
		this.password = password;
		this.answer = answer;
	}

	public Account(int customerId, int controlQuestionId, double cash, String username, String password,
			String answer) {
		super();
		this.customerId = customerId;
		this.controlQuestionId = controlQuestionId;
		this.cash = cash;
		this.username = username;
		this.password = password;
		this.answer = answer;
	}

	public Account(int id, int customerId, int controlQuestionId, double cash, String username, String password,
			String answer) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.controlQuestionId = controlQuestionId;
		this.cash = cash;
		this.username = username;
		this.password = password;
		this.answer = answer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getControlQuestionId() {
		return controlQuestionId;
	}

	public void setControlQuestionId(int controlQuestionId) {
		this.controlQuestionId = controlQuestionId;
	}

	public double getCash() {
		return cash;
	}

	public void setCash(double cash) {
		this.cash = cash;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", customerId=" + customerId + ", controlQuestionId=" + controlQuestionId
				+ ", cash=" + cash + ", username=" + username + ", password=" + password + ", answer=" + answer + "]";
	}
}