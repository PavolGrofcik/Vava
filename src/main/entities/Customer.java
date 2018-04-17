package main.entities;

import java.text.ParseException;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="customer")
public class Customer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="date")			//Dátum narodenia - date vs string
	private Date date;
	
	@Column(name="sex")
	private char sex;
	
	@Column(name="tel_number")
	private String telNumber;
	
	@Column(name="city")
	private String city;
	
	@Column(name="address")
	private String address;
	
	//Default konštruktor pre session.get() method
	public Customer() {
	}


	public Customer(int id, String firstName, String lastName, char sex, String telNumber, String city,
			String address) {
		super();
		
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.sex = sex;
		this.telNumber = telNumber;
		this.city = city;
		this.address = address;
	}

	
	public Customer(Integer id, String firstName, String lastName, Date date, char sex, String telNumber, String city,
			String address) throws ParseException {
		super();
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.date = new Date();
		this.date = date;
		this.sex = sex;
		this.telNumber = telNumber;
		this.city = city;
		this.address = address;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public char getSex() {
		return sex;
	}

	public void setSex(char sex) {
		this.sex = sex;
	}

	public String getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
	public void showCustomerInfo() {
		System.out.println("Id: "+ id + " Name: " + firstName + " Surname: " + lastName  
				+ " Sex: " + sex + " Date of birth: " + date +  " City: " + city + " Address: " + address
				+ " Tel Number: " + telNumber);
	}
}
