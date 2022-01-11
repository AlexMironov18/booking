package ticket.service.system.booking.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "flight")
@EqualsAndHashCode(callSuper = true)
public class Flight extends TimeStampedEntity {

    @Id
    @Type(type = "pg-uuid")
    @Column(name = "flight_id")
    private UUID id = UUID.randomUUID();

    @Column(name = "departure")
    private String departure;

    @Column(name = "destination")
    private String destination;

    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    @NotAudited
    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Ticket> tickets = new ArrayList<>();

    public void addTickets(Ticket ticket) {
        tickets.add(ticket);
        ticket.setFlight(this);
    }

    public void removeTickets(Ticket ticket) {
        tickets.remove(ticket);
        ticket.setFlight(null);
    }
}
