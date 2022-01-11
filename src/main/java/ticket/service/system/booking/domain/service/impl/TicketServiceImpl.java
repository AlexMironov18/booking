package ticket.service.system.booking.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ticket.service.system.booking.domain.entity.Customer;
import ticket.service.system.booking.domain.entity.Ticket;
import ticket.service.system.booking.domain.entity.TicketStatus;
import ticket.service.system.booking.domain.exception.TicketNotFoundException;
import ticket.service.system.booking.domain.exception.WrongUserException;
import ticket.service.system.booking.domain.service.TicketService;
import ticket.service.system.booking.repository.TicketRepository;

import java.util.UUID;

@Component
@Transactional
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    @Override
    public Ticket book(UUID ticketId, Customer customer) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> TicketNotFoundException.id(ticketId));
        ticket.setCustomer(customer);
        ticket.setStatus(TicketStatus.BOOKED);
        return ticketRepository.save(ticket);
        //send kafka message for notification-service "You booked the ticket(all ticket info + flight info and customer info)
    }

    @Override
    public Ticket cancel(UUID ticketId, String userId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> TicketNotFoundException.id(ticketId));
        Customer customer = ticket.getCustomer();
        if (customer != null && !userId.equals(customer.getUserId())) {
            throw WrongUserException.id(userId);
        }
        ticket.setCustomer(null);
        ticket.setStatus(TicketStatus.VACANT);
        return ticketRepository.save(ticket);
        //send kafka message for notification-service "You unbooked the ticket(all ticket info + flight info and customer info)
    }

    @Override
    @Transactional(readOnly = true)
    public Ticket check(UUID ticketId, String userId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> TicketNotFoundException.id(ticketId));
        Customer customer = ticket.getCustomer();
        if (customer != null && !userId.equals(customer.getUserId())) {
            ticket.setCustomer(null);
        }
        return ticket;
    }
}
