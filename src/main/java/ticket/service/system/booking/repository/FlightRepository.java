package ticket.service.system.booking.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ticket.service.system.booking.domain.entity.Flight;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface FlightRepository extends JpaRepository<Flight, UUID> {
    @EntityGraph(attributePaths = "tickets")
    @Query
    Page<Flight> findByDepartureTimeGreaterThanEqualAndArrivalTimeLessThanEqualAndDepartureAndDestination(Pageable pageable,
                                                                                                          LocalDateTime departureTime,
                                                                                                          LocalDateTime arrivalTime,
                                                                                                          String departure,
                                                                                                          String destination);
}
