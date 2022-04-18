package ticket.service.system.booking.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import ticket.service.system.booking.domain.entity.Customer;
import ticket.service.system.booking.domain.entity.Ticket;
import ticket.service.system.booking.domain.service.TicketRepositoryService;
import ticket.service.system.booking.domain.service.TicketService;

import java.util.UUID;

import static ticket.service.system.booking.config.CacheConfig.TICKET_CACHE;

@Component
@RequiredArgsConstructor
public class TicketRepositoryServiceImpl implements TicketRepositoryService {

    private final TicketService ticketService;

    @Override
    @CachePut(value = TICKET_CACHE, key = "{#ticketId, #customer.userId}")
    public Ticket book(UUID ticketId, Customer customer) {
        return ticketService.book(ticketId, customer);
    }

    @Override
    @CachePut(value = TICKET_CACHE, key = "{#ticketId, #userId}")
    public Ticket cancel(UUID ticketId, String userId) {
        return ticketService.cancel(ticketId, userId);
    }

    @Override
    @Cacheable(value = TICKET_CACHE, key="{#ticketId, #userId}")
    public Ticket check(UUID ticketId, String userId) {
        return ticketService.check(ticketId, userId);
    }
}
