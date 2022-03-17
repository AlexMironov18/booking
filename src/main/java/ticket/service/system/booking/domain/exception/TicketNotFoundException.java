package ticket.service.system.booking.domain.exception;

import java.util.UUID;

public class TicketNotFoundException extends RuntimeException {

    public TicketNotFoundException(String message) {
        super(message);
    }

    public static TicketNotFoundException id(UUID id) {
        return new TicketNotFoundException("Ticket not found for ticketId=" + id);
    }
}
