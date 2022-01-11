package ticket.service.system.booking.domain.service;

import ticket.service.system.booking.domain.entity.Ticket;

public interface NotificationService {
    boolean notifyBooking(Ticket ticket);
    boolean notifyCancelBooking(Ticket ticket);
}
