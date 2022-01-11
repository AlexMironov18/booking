package ticket.service.system.booking.web.dto.info;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ticket.service.system.booking.domain.entity.TicketStatus;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketInfoDto {

    @JsonProperty(value = "ticket_id")
    private UUID id;

    @JsonProperty(value = "place")
    private int place;

    @JsonProperty(value = "price")
    private BigDecimal price;

    @JsonProperty(value = "status")
    private TicketStatus status;

}
