package main.entities;


import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	
	@Column(name="birth")			//D�tum narodenia - date vs string
	//@Temporal(TemporalType.DATE)	//Iba date, calendar
	private LocalDate birth;
	
	@Column(name="sex")
	private char sex;
	
	@Column(name="tel_number")
	private String telNumber;
	
	@Column(name="city")
	private String city;
	
	@Column(name="address")
	private String address;
	
	@Column(name="email")
	private String email;
	
	public Customer() {}

	public Customer(int id, String firstName, String lastName, char sex, String telNumber, String email) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.sex = sex;
		this.telNumber = telNumber;
		this.email = email;
	}


	public Customer(String firstName, String lastName, LocalDate date, char sex, String telNumber, String city,
			String address, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.birth = date;
		this.sex = sex;
		this.telNumber = telNumber;
		this.city = city;
		this.address = address;
		this.email = email;
	}


	public Customer(int id, String firstName, String lastName, LocalDate date, char sex, String telNumber, String city,
			String address, String email) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birth = date;
		this.sex = sex;
		this.telNumber = telNumber;
		this.city = city;
		this.address = address;
		this.email = email;
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

	public LocalDate getDate() {
		return birth;
	}

	public void setDate(LocalDate date) {
		this.birth = date;
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
	
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public void showCustomerInfo() {
		System.out.println("Id: "+ id + " Name: " + firstName + " Surname: " + lastName  
				+ " Sex: " + sex + " Date of birth: " + birth +  " City: " + city + " Address: " + address
				+ " Tel Number: " + telNumber);
	}
}
