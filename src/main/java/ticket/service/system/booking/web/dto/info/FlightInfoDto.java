package ticket.service.system.booking.web.dto.info;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlightInfoDto {

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

}
