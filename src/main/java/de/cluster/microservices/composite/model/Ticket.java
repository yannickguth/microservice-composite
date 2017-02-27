package de.cluster.microservices.composite.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;


public class Ticket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3428423207045373647L;
	
	private String id;
	
	private String name;
	
	private double price;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	private Date date;
	
	private int quantity;
	
	public Ticket(String id, String name, double price, Date date, int quantity){
		this.setId(id);
		this.setName(name);
		this.setPrice(price);
		this.setDate(date);
		this.setQuantity(quantity);
	}
	
	public Ticket(){
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		return "Ticket[id="+id+", name="+name+", price="+price+", date="+date+", quantity="+quantity+"]";
	}
	

}
