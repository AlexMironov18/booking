package ticket.service.system.booking.web.mapper;

import org.mapstruct.Mapper;
import ticket.service.system.booking.domain.entity.Ticket;
import ticket.service.system.booking.web.dto.TicketDto;

@Mapper(componentModel = "spring")
public interface TicketToTicketDtoMapper {
    TicketDto map(Ticket ticket);
}
