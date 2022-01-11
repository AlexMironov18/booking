package ticket.service.system.booking.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ticket.service.system.booking.domain.entity.Customer;
import ticket.service.system.booking.domain.entity.Ticket;
import ticket.service.system.booking.domain.service.BookingService;
import ticket.service.system.booking.domain.service.NotificationService;
import ticket.service.system.booking.domain.service.TicketService;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final TicketService ticketRepositoryService;
    private final NotificationService notificationService;

    @Override
    public Ticket book(UUID ticketId, Customer customer) {
        var bookedTicket = ticketRepositoryService.book(ticketId, customer);
        notificationService.notifyBooking(bookedTicket);
        return bookedTicket;
    }

    @Override
    public Ticket cancel(UUID ticketId, String userId) {
        var canceledTicked = ticketRepositoryService.cancel(ticketId, userId);
        notificationService.notifyCancelBooking(canceledTicked);
        return canceledTicked;
    }
}
