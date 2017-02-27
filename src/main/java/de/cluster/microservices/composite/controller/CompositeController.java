package de.cluster.microservices.composite.controller;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.cluster.microservices.composite.model.CompositeEvent;
import de.cluster.microservices.composite.model.Event;
//import de.cluster.microservices.composite.model.Location;
//import de.cluster.microservices.composite.model.Ticket;
import de.cluster.microservices.composite.service.CompositeIntegration;
import de.cluster.microservices.composite.service.Util;

@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@RestController
public class CompositeController {

	private static final Logger LOG = LoggerFactory.getLogger(CompositeController.class);

    @Autowired
    CompositeIntegration integration;

    @RequestMapping("/")
    public String getTicket() {
        return "{\"timestamp\":\"" + new Date() + "\",\"content\":\"Hello from CompositeAPI\"}";
    }
    

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public ResponseEntity<CompositeEvent[]> getCompositeEvents() {
    	return integration.getCompositeEvents();
    }
    
    @RequestMapping(value = "/events/name/{name}", method = RequestMethod.GET)
    public ResponseEntity<CompositeEvent[]> getCompositeEventsByName(@PathVariable String name) {
    	return integration.getCompositeEventsByName(name);
    }
    
    @RequestMapping(value = "/events", method = RequestMethod.POST)
    public ResponseEntity<String> createEvent(@RequestBody Event e) {
    	LOG.info("CREATE EVENT CONTROLLER");
    	integration.createEvent(e);
    	return new ResponseEntity<>("{}",HttpStatus.OK);
    }

    @RequestMapping(value = "/events", method = RequestMethod.PUT)
    public ResponseEntity<CompositeEvent[]> modifyCompositeEvent(@RequestBody CompositeEvent compositeEvent) {
    	return null;
    }
    
    /*

    @RequestMapping(value = "/events/{eventId}", method = RequestMethod.DELETE)
    public ResponseEntity<CompositeEvent[]> deleteCompositeEvent(@PathVariable String eventId) {
    	return null;
    }
         
    @RequestMapping(value = "/events/{eventId}", method = RequestMethod.GET)
    public ResponseEntity<CompositeEvent> getEvent(@PathVariable String eventId) {
    	return integration.getCompositeEvent(eventId);
    }
    
    @RequestMapping(value = "/locations", method = RequestMethod.GET)
    public ResponseEntity<Location> getLocations() {
    	return null;
    }
    
    @RequestMapping("/tickets/{ticketId}")
    public ResponseEntity<Ticket> getTicket(@PathVariable int ticketId) {

        // 1. First get mandatory ticket information
        ResponseEntity<Ticket> ticketResult = integration.getTicket(ticketId);

        if (!ticketResult.getStatusCode().is2xxSuccessful()) {
            // We can't proceed, return whatever fault we got from the getTicket call
            return util.createResponse(null, ticketResult.getStatusCode());
        }
        
        /*

        // 2. Get optional recommendations
        List<Recommendation> recommendations = null;
        try {
            ResponseEntity<List<Recommendation>> recommendationResult = integration.getRecommendations(productId);
            if (!recommendationResult.getStatusCode().is2xxSuccessful()) {
                // Something went wrong with getRecommendations, simply skip the recommendation-information in the response
                LOG.debug("Call to getRecommendations failed: {}", recommendationResult.getStatusCode());
            } else {
                recommendations = recommendationResult.getBody();
            }
        } catch (Throwable t) {
            LOG.error("getProduct erro ", t);
            throw t;
        }


        // 3. Get optional reviews
        ResponseEntity<List<Review>> reviewsResult = integration.getReviews(productId);
        List<Review> reviews = null;
        if (!reviewsResult.getStatusCode().is2xxSuccessful()) {
            // Something went wrong with getReviews, simply skip the review-information in the response
            LOG.debug("Call to getReviews failed: {}", reviewsResult.getStatusCode());
        } else {
            reviews = reviewsResult.getBody();
        }
        

        return util.createOkResponse(ticketResult.getBody());
    }
    */   
    
}