package ticket.service.system.booking.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ticket.service.system.booking.web.dto.info.TicketInfoDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlightDto {

    @JsonProperty(value = "flight_id")
    private UUID id;

    @JsonProperty(value = "departure")
    private String departure;

    @JsonProperty(value = "destination")
    private String destination;

    @JsonProperty(value = "departure_time")
    private LocalDateTime departureTime;

    @JsonProperty(value = "arrival_time")
    private LocalDateTime arrivalTime;

    @JsonProperty(value = "tickets")
    private List<TicketInfoDto> tickets;
}
