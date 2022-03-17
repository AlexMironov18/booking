package ticket.service.system.booking.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ticket.service.system.booking.domain.entity.Customer;
import ticket.service.system.booking.domain.entity.Ticket;
import ticket.service.system.booking.domain.entity.TicketStatus;
import ticket.service.system.booking.domain.exception.TicketNotFoundException;
import ticket.service.system.booking.domain.exception.WrongUserException;
import ticket.service.system.booking.domain.service.BookingService;
import ticket.service.system.booking.domain.service.TicketService;
import ticket.service.system.booking.web.dto.BookingRequest;
import ticket.service.system.booking.web.dto.TicketDto;
import ticket.service.system.booking.web.mapper.BookingRequestToCustomerMapper;
import ticket.service.system.booking.web.mapper.TicketToTicketDtoMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTestWithNoSecurity(
    controllers = TicketController.class,
    includeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = {
            TicketToTicketDtoMapper.class,
            BookingRequestToCustomerMapper.class,
        }
))
class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookingService bookingService;
    @MockBean
    private TicketService ticketService;

    private final TicketToTicketDtoMapper ticketToTicketDtoMapper = Mappers.getMapper(TicketToTicketDtoMapper.class);

    private final String PATH = "/ticket-controller/{ticketId}";
    private final UUID ticketId = UUID.randomUUID();
    private final String userId = "1";

    @Test
    @DisplayName("Ticket booking successfully performed")
    void book() throws Exception {
        var request = BookingRequest.builder()
                .name("Eva")
                .email("someemail@gmail.com")
                .birthDate(LocalDate.now().minusYears(15))
                .passport("123456")
                .build();

        var expectedTicket = createTicket();
        var expectedTicketDto = ticketToTicketDtoMapper.map(expectedTicket);
        when(bookingService.book(eq(ticketId), any(Customer.class))).thenReturn(expectedTicket);

        mockMvc.perform(post(PATH + "/book", ticketId)
                        .param("userId", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(MvcTestUtils.containsObjectAsJson(expectedTicketDto, TicketDto.class));
    }

    @Test
    @DisplayName("Ticket booking request validation error (passport validation)")
    void book_wrongRequest() throws Exception {
        var request = BookingRequest.builder()
                .name("Eva")
                .email("someemail@gmail.com")
                .birthDate(LocalDate.now().minusYears(15))
                .passport("12345678")
                .build();

        mockMvc.perform(post(PATH + "/book", ticketId)
                        .param("userId", userId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Ticket cancelling successfully performed")
    void cancel() throws Exception {
        var expectedTicket = createTicket();
        var expectedTicketDto = ticketToTicketDtoMapper.map(expectedTicket);
        when(bookingService.cancel(ticketId, "1")).thenReturn(expectedTicket);

        mockMvc.perform(delete(PATH + "/book", ticketId)
                        .param("userId", userId))
                .andExpect(status().isOk())
                .andExpect(MvcTestUtils.containsObjectAsJson(expectedTicketDto, TicketDto.class));
    }

    @Test
    @DisplayName("Ticket cancelling wrong user exception")
    void cancel_wrongUser() throws Exception {
        when(bookingService.cancel(ticketId, "1")).thenThrow(WrongUserException.id(userId));

        mockMvc.perform(delete(PATH + "/book", ticketId)
                        .param("userId", userId))
                .andExpect(status().isForbidden())
                .andExpect(MvcTestUtils.hasMessage("Action is not allowed for current user with userId=" + userId));
    }

    @Test
    @DisplayName("Ticket check successfully performed")
    void check() throws Exception {
        var expectedTicket = createTicket();
        var expectedTicketDto = ticketToTicketDtoMapper.map(expectedTicket);
        when(ticketService.check(ticketId, userId)).thenReturn(expectedTicket);

        mockMvc.perform(get(PATH + "/check", ticketId)
                        .param("userId", userId))
                .andExpect(status().isOk())
                .andExpect(MvcTestUtils.containsObjectAsJson(expectedTicketDto, TicketDto.class));
    }

    @Test
    @DisplayName("Ticket check ticket not found exception")
    void check_notFoundTicket() throws Exception {
        when(ticketService.check(ticketId, userId)).thenThrow(TicketNotFoundException.id(ticketId));

        mockMvc.perform(get(PATH + "/check", ticketId)
                        .param("userId", userId))
                .andExpect(status().isNotFound())
                .andExpect(MvcTestUtils.hasMessage("Ticket not found for ticketId=" + ticketId));
    }

    private Ticket createTicket() {
        var ticket = new Ticket();
        ticket.setId(ticketId);
        ticket.setPlace(10);
        ticket.setPrice(BigDecimal.valueOf(100L));
        ticket.setStatus(TicketStatus.BOOKED);
        return ticket;
    }
}