package ticket.service.system.booking.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ticket.service.system.booking.domain.entity.FlightPageCriteria;
import ticket.service.system.booking.domain.entity.FlightSearchCriteria;
import ticket.service.system.booking.domain.service.FlightSearchService;
import ticket.service.system.booking.web.dto.FlightDto;
import ticket.service.system.booking.web.dto.SearchFlightRequest;
import ticket.service.system.booking.web.mapper.FlightSearchCriteriaMapper;
import ticket.service.system.booking.web.mapper.FlightToFlightDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/flight-controller")
@RequiredArgsConstructor
public class FlightController {

    private final FlightSearchService flightSearchService;
    private final FlightToFlightDtoMapper flightToFlightDtoMapper;
    private final FlightSearchCriteriaMapper flightSearchCriteriaMapper;

    @PostMapping(value = "/search")
    @PreAuthorize("hasAnyAuthority('role_user')")
    public ResponseEntity<List<FlightDto>> search(@RequestBody SearchFlightRequest request) {
        FlightSearchCriteria flightSearchCriteria = flightSearchCriteriaMapper.mapSearch(request);
        FlightPageCriteria flightPageCriteria = flightSearchCriteriaMapper.mapPageable(request);
        var result = flightSearchService.search(flightSearchCriteria, flightPageCriteria);
        return ResponseEntity.ok(result.get().map(flightToFlightDtoMapper::map).collect(Collectors.toList()));
    }
}
