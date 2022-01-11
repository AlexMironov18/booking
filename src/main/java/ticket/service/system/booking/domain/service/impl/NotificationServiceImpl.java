package ticket.service.system.booking.domain.service.impl;

import org.springframework.stereotype.Component;
import ticket.service.system.booking.domain.entity.Ticket;
import ticket.service.system.booking.domain.service.NotificationService;

@Component
public class NotificationServiceImpl implements NotificationService {

    @Override
    public boolean notifyBooking(Ticket ticket) {
        return true;
    }

    @Override
    public boolean notifyCancelBooking(Ticket ticket) {
        return true;
    }
}
