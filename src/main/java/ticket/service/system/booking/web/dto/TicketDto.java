package ticket.service.system.booking.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import ticket.service.system.booking.domain.entity.TicketStatus;
import ticket.service.system.booking.web.dto.info.CustomerInfoDto;
import ticket.service.system.booking.web.dto.info.FlightInfoDto;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TicketDto {
    private UUID id;
    private int place;
    private BigDecimal price;
    private TicketStatus status;
    private FlightInfoDto flight;
    private CustomerInfoDto customer;
}
