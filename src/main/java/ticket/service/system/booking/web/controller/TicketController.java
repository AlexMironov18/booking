package ticket.service.system.booking.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ticket.service.system.booking.domain.entity.Customer;
import ticket.service.system.booking.domain.entity.Flight;
import ticket.service.system.booking.domain.entity.Ticket;
import ticket.service.system.booking.domain.entity.TicketStatus;
import ticket.service.system.booking.domain.service.BookingService;
import ticket.service.system.booking.domain.service.TicketService;
import ticket.service.system.booking.repository.FlightRepository;
import ticket.service.system.booking.web.dto.BookingRequest;
import ticket.service.system.booking.web.dto.TicketDto;
import ticket.service.system.booking.web.mapper.BookingRequestToCustomerMapper;
import ticket.service.system.booking.web.mapper.TicketToTicketDtoMapper;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/ticket-controller/{ticketId}")
@RequiredArgsConstructor
public class TicketController {

    private final TicketToTicketDtoMapper ticketToTicketDtoMapper;
    private final BookingRequestToCustomerMapper customerMapper;
    private final FlightRepository flightRepository;
    private final BookingService bookingService;
    private final TicketService ticketService;

    @PostMapping(value = "/book")
    public ResponseEntity<TicketDto> book(@PathVariable(name = "ticketId") UUID ticketId,
                                          @RequestBody @Valid BookingRequest request,
                                          @RequestParam String userId) {
        Customer customer = customerMapper.map(request, userId);
        var bookedTicket = bookingService.book(ticketId, customer);
        return ResponseEntity.ok(ticketToTicketDtoMapper.map(bookedTicket));
    }

    @DeleteMapping(value = "/book")
    public ResponseEntity<TicketDto> cancel(@PathVariable(name = "ticketId") UUID ticketId,
                                            @RequestParam String userId) {
        var canceledTicket = bookingService.cancel(ticketId, userId);
        return ResponseEntity.ok(ticketToTicketDtoMapper.map(canceledTicket));
    }

    @GetMapping(value = "/check")
    public ResponseEntity<TicketDto> check(@PathVariable(name = "ticketId") UUID ticketId,
                                          @RequestParam String userId) {
        var ticket = ticketService.check(ticketId, userId);
        return ResponseEntity.ok(ticketToTicketDtoMapper.map(ticket));
    }





    @PostMapping(value = "/mock")
    public ResponseEntity<String> mock() {
        Random random = new Random();

        Ticket ticket = new Ticket();
        ticket.setPlace((random.nextInt(10) + 1));
        ticket.setStatus(TicketStatus.VACANT);
        ticket.setPrice(BigDecimal.valueOf(100L));
        Ticket ticket2 = new Ticket();
        ticket2.setPlace((random.nextInt(10) + 1));
        ticket2.setStatus(TicketStatus.VACANT);
        ticket2.setPrice(BigDecimal.valueOf(150L));
        Ticket ticket3 = new Ticket();
        ticket3.setPlace((random.nextInt(10) + 1));
        ticket3.setStatus(TicketStatus.VACANT);
        ticket3.setPrice(BigDecimal.valueOf(200L));

        Flight flight = new Flight();
        flight.setDeparture("city10");
        flight.setDestination("city11");
        flight.setArrivalTime(LocalDateTime.now().plusDays((random.nextInt(5) + 1)));
        flight.setDepartureTime(LocalDateTime.now().plusDays(10).plusDays((random.nextInt(5) + 1)));

        flight.addTickets(ticket);
        flight.addTickets(ticket2);
        flight.addTickets(ticket3);
        flightRepository.save(flight);
        return ResponseEntity.ok("ok");
    }
}
