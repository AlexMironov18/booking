package ticket.service.system.booking.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class WrongUserException extends RuntimeException {

    public WrongUserException(String message) {
        super(message);
    }

    public static WrongUserException id(String userId) {
        return new WrongUserException("Action is not allowed for current user with userId=" + userId);
    }

}
