package user_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GreenMarketException extends RuntimeException {
    private final HttpStatus status;
    private final String errorMessage;

    public GreenMarketException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.status = errorCode.getStatus();
        this.errorMessage = errorCode.getErrorMessage();
    }
}
