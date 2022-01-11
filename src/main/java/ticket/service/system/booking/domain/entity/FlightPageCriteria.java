package ticket.service.system.booking.domain.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
@Builder(toBuilder = true)
public class FlightPageCriteria {
    private int pageNum;
    private int pageSize;
    private Sort.Direction sortDirection;
    private String sortBy;
}
