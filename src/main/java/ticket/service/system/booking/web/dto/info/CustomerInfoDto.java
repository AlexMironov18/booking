package ticket.service.system.booking.web.dto.info;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerInfoDto {

    @JsonProperty(value = "customer_id")
    private UUID id;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "last_name")
    private String lastName;

    @JsonProperty(value = "middle_name")
    private String middleName;

    @JsonProperty(value = "birth_date")
    private LocalDate birthDate;

    @JsonProperty(value = "passport")
    private String passport;
}
