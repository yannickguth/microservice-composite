package de.cluster.microservices.composite.service;

import de.cluster.microservices.composite.model.CompositeEvent;
import de.cluster.microservices.composite.model.Event;
import de.cluster.microservices.composite.model.Location;
import de.cluster.microservices.composite.model.Ticket;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Created by Yannick Guth on 01.03.17.
 */

@Service
public interface CompositeService {

    public ResponseEntity<CompositeEvent[]> getCompositeEvents();

    public ResponseEntity<CompositeEvent[]> processEvents(ResponseEntity<Event[]> revents);

    public ResponseEntity<CompositeEvent> getCompositeEvent(String eventId);

    public CompositeEvent buildCompositeEvent(Event e);

    public void createEvent(Event e);

    public void validateNewEvent(Event e);

    public Location getLocationOrNull(String locationId);

    public Ticket getTicketOrNull(String ticketId);
}
