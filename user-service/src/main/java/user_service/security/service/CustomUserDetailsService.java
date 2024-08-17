package user_service.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import user_service.entity.User;
import user_service.exception.ErrorCode;
import user_service.exception.GreenMarketException;
import user_service.repository.UserRepository;
import user_service.security.principal.PrincipalDetails;
import user_service.security.principal.UserDto;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public PrincipalDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User findUser = userRepository.findByEmail(email)
            .orElseThrow(() -> new GreenMarketException(ErrorCode.USER_NOT_FOUND));
        UserDto userDto = UserDto.builder()
            .id(findUser.getId())
            .role(findUser.getRole())
            .build();
        return new PrincipalDetails(userDto);
    }
}
