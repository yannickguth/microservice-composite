package de.cluster.microservices.composite.controller;

import de.cluster.microservices.composite.model.CompositeEvent;
import de.cluster.microservices.composite.model.Event;
import de.cluster.microservices.composite.service.CompositeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@RestController
public class CompositeController {

	private static final Logger LOG = LoggerFactory.getLogger(CompositeController.class);

    @Autowired
    CompositeService compositeService;

    @RequestMapping(value = "/composite", method = RequestMethod.GET)
    public ResponseEntity<CompositeEvent[]> getCompositeEvents() {
        LOG.info("Fetching all composite-events");
        return compositeService.getCompositeEvents();
    }

    @RequestMapping(value = "/composite", method = RequestMethod.POST)
    public ResponseEntity<String> createEvent(@RequestBody Event e) {
        LOG.info("Creating composite-event["+e+"]");
        compositeService.createEvent(e);
    	return new ResponseEntity<>("{}",HttpStatus.OK);
    }
    
}