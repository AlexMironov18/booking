package ticket.service.system.booking.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TicketNotFoundException extends RuntimeException {

    public TicketNotFoundException(String message) {
        super(message);
    }

    public static TicketNotFoundException id(UUID id) {
        return new TicketNotFoundException("Ticket not found for ticketId=" + id);
    }
}
