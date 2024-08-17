package user_service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    EMAIL_ALREADY_EXIST(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    PASSWORD_INVALID(HttpStatus.BAD_REQUEST, "패스워드가 틀립니다."),
    ACCESS_TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "토큰이 만료되었습니다.");

    private final HttpStatus status;
    private final String errorMessage;
}
