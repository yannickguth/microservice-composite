package de.cluster.microservices.composite.model;

public class CompositeEvent {
    
    private Event event;
    private Location location;
    private Ticket ticket;
    
    public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public CompositeEvent() {
    	
    }

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public CompositeEvent(Event e, Location l, Ticket t) {
    	event = e;
    	location = l;
    	ticket = t;
    }
}
