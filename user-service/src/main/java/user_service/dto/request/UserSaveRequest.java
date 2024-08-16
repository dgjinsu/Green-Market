package user_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import user_service.entity.Role;

@NoArgsConstructor
@Getter
public class UserSaveRequest {

    @Schema(description = "이메일", example = "abcde@naver.com")
    private String email; // 이메일
    @Schema(description = "로그인 패스워드", example = "abcde")
    private String password;
    @Schema(description = "이름", example = "김진수")
    private String name; // 이름
    @Schema(description = "휴대폰 번호", example = "01012345678")
    private String phone;
    @Schema(description = "회원 타입", example = "SELLER")
    private Role role; // 회원 타입
}
