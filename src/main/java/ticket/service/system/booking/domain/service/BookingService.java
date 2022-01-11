package ticket.service.system.booking.domain.service;

import ticket.service.system.booking.domain.entity.Customer;
import ticket.service.system.booking.domain.entity.Ticket;

import java.util.UUID;

public interface BookingService {
    Ticket book(UUID ticketId, Customer customer);
    Ticket cancel(UUID ticketId, String userId);
}
