package ticket.service.system.booking.domain.service;

import org.springframework.data.domain.Page;
import ticket.service.system.booking.domain.entity.Flight;
import ticket.service.system.booking.domain.entity.FlightPageCriteria;
import ticket.service.system.booking.domain.entity.FlightSearchCriteria;

public interface FlightSearchService {
    Page<Flight> find(FlightSearchCriteria searchCondition, FlightPageCriteria pageCriteria);
}
