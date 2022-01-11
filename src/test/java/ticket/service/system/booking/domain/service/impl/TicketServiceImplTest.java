package ticket.service.system.booking.domain.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import ticket.service.system.booking.domain.entity.Customer;
import ticket.service.system.booking.domain.entity.TicketStatus;
import ticket.service.system.booking.domain.exception.TicketNotFoundException;
import ticket.service.system.booking.domain.exception.WrongUserException;
import ticket.service.system.booking.repository.TicketRepository;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(TicketServiceImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TicketServiceImplTest {

    @Autowired
    private TicketServiceImpl ticketService;
    @Autowired
    private TicketRepository ticketRepository;

    @Test
    @Sql("/flights.sql")
    void testBooking() {
        UUID ticketId = UUID.fromString("457d6614-5ad8-44e4-a569-02eb5d3652ea");
        Customer customer = getCustomer("1");

        var ticket = ticketRepository.findById(ticketId);
        assertAll(
                () -> assertTrue(ticket.isPresent()),
                () -> assertEquals(TicketStatus.VACANT, ticket.get().getStatus())
        );

        var bookedTicket = ticketService.book(ticketId, customer);
        assertAll(
            () -> assertEquals(TicketStatus.BOOKED, bookedTicket.getStatus()),
            () -> assertEquals(customer, bookedTicket.getCustomer())
        );
    }

    @Test
    @Sql("/flights.sql")
    void testBookingNotExistingTicket() {
        UUID ticketId = UUID.fromString("457d6614-5ad8-44e4-a569-02eb5d365211");
        Customer customer = getCustomer("1");

        TicketNotFoundException exception = assertThrows(
                TicketNotFoundException.class,
                () -> ticketService.book(ticketId, customer)
        );

        assertEquals("Ticket not found for ticketId=" + ticketId, exception.getMessage());
    }

    @Test
    @Sql("/booked-ticket.sql")
    void testCancelBooking() {
        UUID ticketId = UUID.fromString("634bc048-c6d9-4d36-b04a-a2e8700ca0a3");
        String userId = "1";

        var ticket = ticketRepository.findById(ticketId);
        assertAll(
                () -> assertTrue(ticket.isPresent()),
                () -> assertEquals(TicketStatus.BOOKED, ticket.get().getStatus())
        );

        var cancelledTicket = ticketService.cancel(ticketId, userId);
        assertAll(
                () -> assertEquals(TicketStatus.VACANT, cancelledTicket.getStatus()),
                () -> assertNull(cancelledTicket.getCustomer())
        );
    }

    @Test
    @Sql("/booked-ticket.sql")
    void testCancelBookingWrongUser() {
        UUID ticketId = UUID.fromString("634bc048-c6d9-4d36-b04a-a2e8700ca0a3");
        String userId = "someUserId";

        WrongUserException exception = assertThrows(
                WrongUserException.class,
                () -> ticketService.cancel(ticketId, userId)
        );

        assertEquals("Action is not allowed for current user with userId=" + userId, exception.getMessage());
    }

    private Customer getCustomer(String userId) {
        Customer customer = new Customer();
        customer.setPassport("111111");
        customer.setName("CustomerName");
        customer.setLastName("CustomerLastName");
        customer.setMiddleName("CustomerMiddleName");
        customer.setBirthDate(LocalDate.now().minusYears(40L));
        customer.setUserId(userId);
        return customer;
    }

}