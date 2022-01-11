package ticket.service.system.booking.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ticket.service.system.booking.domain.entity.FlightPageCriteria;
import ticket.service.system.booking.domain.entity.FlightSearchCriteria;
import ticket.service.system.booking.web.dto.SearchFlightRequest;

import java.time.LocalTime;

@Mapper(componentModel = "spring", imports = LocalTime.class)
public interface FlightSearchCriteriaMapper {

    @Mapping(target = "departureTimeFrom", expression = "java(request.getDepartureDateFrom() != null ? request.getDepartureDateFrom().atTime(LocalTime.MAX) : null)")
    @Mapping(target = "departureTimeTo", expression = "java(request.getDepartureDateTo() != null ? request.getDepartureDateTo().atTime(LocalTime.MIN) : null)")
    FlightSearchCriteria mapSearch(SearchFlightRequest request);

    FlightPageCriteria mapPageable(SearchFlightRequest request);
}
