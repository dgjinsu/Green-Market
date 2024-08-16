package user_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LoginRequest {

    @Schema(description = "이메일", example = "abcde@naver.com")
    private String email; // 이메일
    @Schema(description = "로그인 패스워드", example = "abcde")
    private String password;
}
