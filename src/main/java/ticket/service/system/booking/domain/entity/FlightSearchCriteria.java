package ticket.service.system.booking.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
public class FlightSearchCriteria {
    private LocalDateTime departureTimeFrom;
    private LocalDateTime departureTimeTo;
    private String arrivalPlace;
    private String departurePlace;
}
