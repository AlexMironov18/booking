package ticket.service.system.booking.domain.entity;

import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Audited
@Table(name = "ticket")
@EqualsAndHashCode(callSuper = true)
public class Ticket extends TimeStampedEntity {

    @Id
    @Type(type = "pg-uuid")
    @Column(name = "ticket_id")
    private UUID id = UUID.randomUUID();

    @Column(name = "place")
    private int place;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @NotAudited
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id")
    @ToString.Exclude
    private Flight flight;

    @NotAudited
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "customer_id")
    @ToString.Exclude
    private Customer customer;
}
