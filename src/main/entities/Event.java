package main.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="event")
public class Event {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="location")
	private String location;
	
	@Column(name="e_start")
	private Date start;
	
	@Column(name="length")
	private int length;
	
	@Column(name="height")
	private double height;
	
	@Column(name="insurance")
	private boolean insurance;
	
	@Column(name="price")
	private double price;
	
	@Column(name = "url")
	private String url;
	
	public Event() {}

	public Event(int id, String location, Date start, int length, double height, boolean insurance) {
		super();
		this.id = id;
		this.location = location;
		this.start = start;
		this.length = length;
		this.height = height;
		this.insurance = insurance;
	}

	public Event(int id, String location, Date start, int length, double height, boolean insurance, double price) {
		super();
		this.id = id;
		this.location = location;
		this.start = start;
		this.length = length;
		this.height = height;
		this.insurance = insurance;
		this.price = price;
	}

	public Event(int id, String location, Date start, int length, double height, boolean insurance, double price,
			String url) {
		super();
		this.id = id;
		this.location = location;
		this.start = start;
		this.length = length;
		this.height = height;
		this.insurance = insurance;
		this.price = price;
		this.url = url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isInsurance() {
		return insurance;
	}

	public void setInsurance(boolean insurance) {
		this.insurance = insurance;
	}
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void showEventInfo() {
		System.out.println("Id: " + id + " Location: " + location + " Start: " + start + 
				" Length: " + length + " Height: " + height + " Insurance: " + insurance);
	}
	
}
