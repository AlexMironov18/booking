package ticket.service.system.booking.domain.service.impl;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ticket.service.system.booking.domain.entity.Flight;
import ticket.service.system.booking.domain.entity.FlightPageCriteria;
import ticket.service.system.booking.domain.entity.FlightSearchCriteria;
import ticket.service.system.booking.domain.entity.Ticket;
import ticket.service.system.booking.domain.service.FlightSearchService;
import ticket.service.system.booking.domain.entity.FlightSearchProjection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class FlightSearchServiceImpl implements FlightSearchService {

    @PersistenceContext
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public FlightSearchServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Flight> search(FlightSearchCriteria searchCondition, FlightPageCriteria pageCriteria) {
        //to fix HHH000104
        //to avoid n+1 select when fetching related entity and pagination
        //first select projection (or some field), since selecting whole entity itself would fetch related one automatically
        //having found paginated ids of main entity - just select by id without pagination fetching related entities
        Pageable pageable = getPageable(pageCriteria);
        CriteriaQuery<FlightSearchProjection> queryForIds = criteriaBuilder.createQuery(FlightSearchProjection.class);
        Root<Flight> rootFlightForIds = queryForIds.from(Flight.class);
        Join<Flight, Ticket> tickets = rootFlightForIds.join("tickets", JoinType.LEFT);
        Predicate predicateForIds = criteriaBuilder.and(createPredicates(searchCondition, rootFlightForIds, tickets).toArray(Predicate[]::new));

        setOrder(pageCriteria, queryForIds, rootFlightForIds);
        queryForIds.multiselect(rootFlightForIds.get("id"), rootFlightForIds.get("departureTime"))
                    .where(predicateForIds)
                    .distinct(true);

        List<FlightSearchProjection> flightsProjection = entityManager.createQuery(queryForIds)
                .setFirstResult((pageCriteria.getPageNum() - 1) * pageCriteria.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        CriteriaQuery<Flight> query = criteriaBuilder.createQuery(Flight.class);
        Root<Flight> flightRoot = query.from(Flight.class);
        flightRoot.fetch("tickets", JoinType.LEFT);
        Predicate predicate = criteriaBuilder.or(
                            flightsProjection.stream()
                            .map(FlightSearchProjection::getFlightId)
                            .map(id -> criteriaBuilder.equal(flightRoot.get("id"), id)
                        )
                        .toArray(Predicate[]::new));

        setOrder(pageCriteria, query, flightRoot);
        query.select(flightRoot)
                .where(predicate)
                .distinct(true);

        List<Flight> flights = entityManager.createQuery(query).getResultList();
        long count = getFlightCount(searchCondition);

        return new PageImpl<>(flights, pageable, count);
    }

    private long getFlightCount(FlightSearchCriteria searchCondition) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Flight> flight = countQuery.from(Flight.class);
        Join<Flight, Ticket> tickets = flight.join("tickets", JoinType.LEFT);
        Predicate predicate = criteriaBuilder.and(createPredicates(searchCondition, flight, tickets).toArray(Predicate[]::new));
        countQuery.select(criteriaBuilder.countDistinct(flight)).where(predicate).distinct(true);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private Pageable getPageable(FlightPageCriteria pageCriteria) {
        Sort sort = Sort.by(pageCriteria.getSortDirection(), pageCriteria.getSortBy());
        return PageRequest.of(pageCriteria.getPageNum(), pageCriteria.getPageSize(), sort);
    }

    private void setOrder(FlightPageCriteria pageCriteria, CriteriaQuery<?> criteriaQuery, Root<Flight> flight) {
        if (Sort.Direction.ASC == pageCriteria.getSortDirection()) {
            criteriaQuery.orderBy(criteriaBuilder.asc(flight.get(pageCriteria.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(flight.get(pageCriteria.getSortBy())));
        }
    }

    private List<Predicate> createPredicates(FlightSearchCriteria searchCondition, Root<Flight> flight, Join<Flight, Ticket> tickets) {
        List<Predicate> predicates = new ArrayList<>();
        if (searchCondition.getDepartureTimeFrom() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(flight.get("departureTime"), searchCondition.getDepartureTimeFrom()));
        }
        if (searchCondition.getDepartureTimeTo() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(flight.get("arrivalTime"), searchCondition.getDepartureTimeTo()));
        }
        if (searchCondition.getArrivalPlace() != null) {
            predicates.add(criteriaBuilder.equal(flight.get("destination"), searchCondition.getArrivalPlace()));
        }
        if (searchCondition.getDeparturePlace() != null) {
            predicates.add(criteriaBuilder.equal(flight.get("departure"), searchCondition.getDeparturePlace()));
        }
        if (searchCondition.getMaxTicketPrice() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(tickets.get("price"), searchCondition.getMaxTicketPrice()));
        }
        return predicates;
    }
}
