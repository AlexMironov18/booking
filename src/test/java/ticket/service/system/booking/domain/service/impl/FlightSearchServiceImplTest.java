package ticket.service.system.booking.domain.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import ticket.service.system.booking.domain.entity.Flight;
import ticket.service.system.booking.domain.entity.FlightPageCriteria;
import ticket.service.system.booking.domain.entity.FlightSearchCriteria;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(FlightSearchServiceImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FlightSearchServiceImplTest {

    @Autowired
    private FlightSearchServiceImpl flightSearchService;

    @Sql("/flights.sql")
    @ParameterizedTest
    @MethodSource("searchCriteria")
    void testSearchCriteria(String departurePlace, String arrivalPlace,
                            LocalDateTime departureTimeFrom, LocalDateTime departureTimeTo) {
        FlightSearchCriteria searchCondition = FlightSearchCriteria.builder()
                .arrivalPlace(arrivalPlace)
                .departurePlace(departurePlace)
                .departureTimeFrom(departureTimeFrom)
                .departureTimeTo(departureTimeTo)
                .build();
        FlightPageCriteria pageCriteria = FlightPageCriteria.builder()
                .pageNum(1)
                .pageSize(10)
                .sortDirection(Sort.Direction.ASC)
                .sortBy("departureTime")
                .build();

        var result = flightSearchService.search(searchCondition, pageCriteria);
        Assertions.assertAll(
                getAssertions(result, departurePlace, arrivalPlace, departureTimeFrom, departureTimeTo)
        );
    }

    private static Stream<Arguments> searchCriteria() {
        return Stream.of(
                Arguments.of("London", null, null, null),
                Arguments.of("London", "Paris", null, null),
                Arguments.of(null, null, LocalDateTime.of(2015,8,21,15,55), LocalDateTime.of(2015,11,21,19,55)),
                Arguments.of("London", null, LocalDateTime.of(2015,11,21 ,17,48), null)
        );
    }

    private static List<Executable> getAssertions(Page<Flight> result, String departurePlace, String arrivalPlace,
                                              LocalDateTime departureTimeFrom, LocalDateTime departureTimeTo) {
        List<Executable> executables = new ArrayList<>();
        if (departurePlace != null) {
            executables.add(() -> assertThat(result.get()).extracting(Flight::getDeparture).containsOnly(departurePlace));
        }
        if (arrivalPlace != null) {
            executables.add(() -> assertThat(result.get()).extracting(Flight::getDestination).containsOnly(arrivalPlace));
        }
        if (departureTimeFrom != null) {
            executables.add(() -> assertThat(result.get()).extracting(Flight::getDepartureTime).allMatch(departureTime -> departureTime.isAfter(departureTimeFrom)));
        }
        if (departureTimeTo != null) {
            executables.add(() -> assertThat(result.get()).extracting(Flight::getDepartureTime).allMatch(departureTime -> departureTime.isBefore(departureTimeTo)));
        }
        return executables;
    }
}