package ticket.service.system.booking.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchFlightRequest {

    @JsonProperty(value = "departure_date_from")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate departureDateFrom;

    @JsonProperty(value = "departure_date_to")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate departureDateTo;

    @JsonProperty(value = "destination_place")
    private String arrivalPlace;

    @JsonProperty(value = "departure_place")
    private String departurePlace;

    @JsonProperty(value = "max_ticket_price")
    private BigDecimal maxTicketPrice;

    @JsonProperty(value = "page_num")
    private int pageNum = 1;

    @JsonProperty(value = "page_size")
    private int pageSize = 5;

    @JsonProperty(value = "sort_direction")
    private Sort.Direction sortDirection = Sort.Direction.ASC;

    @JsonProperty(value = "sort_by")
    private String sortBy = "departureTime";

}
