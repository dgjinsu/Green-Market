package user_service.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import user_service.dto.request.LoginRequest;
import user_service.dto.request.UserSaveRequest;
import user_service.dto.response.LoginResponse;
import user_service.entity.User;
import user_service.exception.ErrorCode;
import user_service.exception.GreenMarketException;
import user_service.repository.UserRepository;
import user_service.security.filter.JwtTokenProvider;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class LoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 회원 가입
     */
    public Long joinUser(UserSaveRequest request) {
        // 이메일 중복 체크
        validationEmail(request.getEmail());

        User user = User.builder()
            .email(request.getEmail())
            .password(encoder.encode(request.getPassword()))
            .name(request.getName())
            .phone(request.getPhone())
            .role(request.getRole())
            .build();

        return userRepository.save(user).getId();
    }

    public void validationEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            throw new GreenMarketException(ErrorCode.EMAIL_ALREADY_EXIST);
        }
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new GreenMarketException(ErrorCode.USER_NOT_FOUND));

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new GreenMarketException(ErrorCode.PASSWORD_INVALID);
        }

        String accessToken = jwtTokenProvider.createAccessToken(user.getEmail());

        return new LoginResponse(accessToken);
    }
}
