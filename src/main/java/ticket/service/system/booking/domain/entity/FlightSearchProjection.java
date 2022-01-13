package ticket.service.system.booking.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightSearchProjection {
    private UUID flightId;
    private LocalDateTime departureTime;
}
