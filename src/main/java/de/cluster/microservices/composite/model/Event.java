package de.cluster.microservices.composite.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Event {
	private String id;
    
    private String eventName;
    
    private String locationId;
    
    private String ticketId;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date date;
    
    public Event() {
    	
    }
    
	public Event(String eventName, String postalCode, Date date) {
    	this.eventName = eventName;
    	this.locationId = postalCode;
    	this.date = date;
    }
    
    public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

    public String getEventName() {
    	return eventName;
    }
    
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

    public String getLocationId() {
    	return locationId;
    }
    
	public void setLocationId(String postalCode) {
		this.locationId = postalCode;
	}

    public String getId() {
    	return id;
    }
    
    public void setId(String id) {
    	this.id = id;
    }
    
    public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
    
    @Override
    public String toString() {
    	return "Event[id="+id+", name="+eventName+", date="+date+", postalCode="+locationId+"]";
    }
}
