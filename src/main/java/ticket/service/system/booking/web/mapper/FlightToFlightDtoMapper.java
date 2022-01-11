package ticket.service.system.booking.web.mapper;

import org.mapstruct.Mapper;
import ticket.service.system.booking.domain.entity.Flight;
import ticket.service.system.booking.web.dto.FlightDto;

@Mapper(componentModel = "spring", uses = TicketToTicketDtoMapper.class)
public interface FlightToFlightDtoMapper {
    FlightDto map(Flight flight);
}
