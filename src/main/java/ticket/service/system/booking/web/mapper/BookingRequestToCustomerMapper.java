package ticket.service.system.booking.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ticket.service.system.booking.domain.entity.Customer;
import ticket.service.system.booking.web.dto.BookingRequest;

@Mapper(componentModel = "spring")
public interface BookingRequestToCustomerMapper {

    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "lastName", source = "request.lastName")
    @Mapping(target = "middleName", source = "request.middleName")
    @Mapping(target = "birthDate", source = "request.birthDate")
    @Mapping(target = "passport", source = "request.passport")
    Customer map(BookingRequest request, String userId);
}
