package main.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer_event")
public class CustomerEvent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "event_id")
	private int eventId;

	@Column(name = "customer_id")
	private int customerId;

	@Column(name = "specification")
	private boolean specification;

	public CustomerEvent() {
	}

	public CustomerEvent(int eventId, int customerId, boolean specification) {
		super();
		this.eventId = eventId;
		this.customerId = customerId;
		this.specification = specification;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public boolean isSpecification() {
		return specification;
	}

	public void setSpecification(boolean specification) {
		this.specification = specification;
	}
}
