package ticket.service.system.booking.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "customer")
@EqualsAndHashCode(callSuper = true)
public class Customer extends TimeStampedEntity {

    @Id
    @Column(name = "customer_id")
    @Type(type = "pg-uuid")
    private UUID id = UUID.randomUUID();

    @Column(name = "user_id")
    private String userId;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "passport")
    private String passport;
}
