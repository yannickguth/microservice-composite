package de.cluster.microservices.composite.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import de.cluster.microservices.composite.model.CompositeEvent;
import de.cluster.microservices.composite.model.Event;
import de.cluster.microservices.composite.model.Location;
import de.cluster.microservices.composite.model.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class CompositeServiceImpl implements CompositeService {
	
	private static final Logger LOG = LoggerFactory.getLogger(CompositeServiceImpl.class);

    @Autowired
    Util util;

    @Autowired
    RestTemplate restTemplate;

    //* Docker Variante *//
    // Values are located in application.yml
    @Value("${servicenames.events}")
    String eventHost;

	@Value("${servicenames.locations}")
    String locationHost;

	@Value("${servicenames.tickets}")
    String ticketHost;
    
    /**
    * Get Composite Events
	 */

    @HystrixCommand (fallbackMethod = "avail")
    public ResponseEntity<CompositeEvent[]> getCompositeEvents() {
		return processEvents(restTemplate.getForEntity("http://"+ eventHost +"/events", Event[].class));
    }
    
    public ResponseEntity<CompositeEvent[]> processEvents(ResponseEntity<Event[]> revents) {

			if (!revents.getStatusCode().is2xxSuccessful()) {
				return new ResponseEntity<>(revents.getStatusCode());
			}

			Event[] events = revents.getBody();
			CompositeEvent[] ces = new CompositeEvent[events.length];

			//get all dependencies
			for (int i = 0; i < events.length; i++) {
				ces[i] = buildCompositeEvent(events[i]);
			}

			return new ResponseEntity<>(ces, HttpStatus.OK);
	}

	public String avail() {
		return "Service currently unavailable";
	}

	@HystrixCommand (fallbackMethod = "avail")
    public ResponseEntity<CompositeEvent> getCompositeEvent(String eventId) {
    	//Get event
    	ResponseEntity<Event> revents = restTemplate.getForEntity("http://"+ eventHost +"/events/"+eventId, Event.class);

    	// return status if response is not successful
    	if(!revents.getStatusCode().is2xxSuccessful()) {
        	return new ResponseEntity<>(revents.getStatusCode());
    	}

    	//get all dependencies
    	return new ResponseEntity<>(buildCompositeEvent(revents.getBody()), HttpStatus.OK);
    }

    public CompositeEvent buildCompositeEvent(Event e) {
    	Location location = getLocationOrNull(e.getLocationId());
		Ticket ticket = getTicketOrNull(e.getTicketId());
		
		return new CompositeEvent(e, location, ticket);
    }

	/**
	 * Create Composite Event
	 */
    public void createEvent(Event e) {
    	LOG.info("Create event "+e.getEventName());
    	validateNewEvent(e);
    	restTemplate.postForEntity("http://"+ eventHost +"/events", e, String.class);
    }

    // Check the validation of created events - tickets are not mandatory.
    public void validateNewEvent(Event e) {
    	if(e == null) {
    		throw new IllegalArgumentException("No event specified.");
    	}
    	if(e.getLocationId() == null) {
    		throw new IllegalArgumentException("No location id specified.");
    	}
    	if(e.getDate() == null) {
    		throw new IllegalArgumentException("No date specified.");
    	}

    	//try to get the specified location
    	restTemplate.getForEntity("http://"+ locationHost +"/locations/"+e.getLocationId(), Location.class);
    }

	/**
	 * Get Locations
	 */

	@HystrixCommand (fallbackMethod = "avail")
    public Location getLocationOrNull(String locationId) {
    	if(locationId == null) {
    		return null;
    	}
    	try {
    		ResponseEntity<Location> rlocation = restTemplate.getForEntity("http://"+ locationHost +"/locations/"+locationId, Location.class);
			return rlocation.getStatusCode().is2xxSuccessful() ? rlocation.getBody() : null;
    	} catch(HttpClientErrorException  e) {
			LOG.error("Location not found. LocationId: " + locationId);
    		return null;
    	}
    }

	/**
	 * Get Tickets
	 */

	@HystrixCommand (fallbackMethod = "avail")
    public Ticket getTicketOrNull(String ticketId) {
    	if(ticketId == null) {
    		return null;
    	}
    	
    	try {
        	ResponseEntity<Ticket> rtickets = restTemplate.getForEntity("http://"+ ticketHost +"/tickets/"+ticketId, Ticket.class);
			return rtickets.getStatusCode().is2xxSuccessful() ? rtickets.getBody() : null;
    	} catch(HttpClientErrorException  e) {
    		LOG.error("Ticket not found. TicketId: " + ticketId);
    		return null;
    	}
    }

}
