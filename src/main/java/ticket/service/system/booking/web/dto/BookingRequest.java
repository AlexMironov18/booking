package ticket.service.system.booking.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import ticket.service.system.booking.web.validation.Email;

import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingRequest {

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "last_name")
    private String lastName;

    @JsonProperty(value = "middle_name")
    private String middleName;

    @Email
    @JsonProperty(value = "email")
    private String email;

    @Past
    @JsonProperty(value = "birth_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Pattern(regexp = "^[0-9]{6}$")
    @JsonProperty(value = "passport")
    private String passport;
}
