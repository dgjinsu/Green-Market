package user_service.security.filter;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import user_service.common.Response;
import user_service.exception.ErrorCode;
import user_service.exception.GreenMarketException;
import user_service.security.principal.PrincipalDetails;
import user_service.security.service.CustomUserDetailsService;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtProvider;
    private final ObjectMapper objectMapper;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        try {
            String authorizationHeader = request.getHeader("Authorization");
            String token;
            String loginId;
            // 헤더가 null 이 아니고 올바른 토큰이라면
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                // 토큰 추출
                token = authorizationHeader.substring(7);
                // 만료 체크
                if (jwtProvider.isExpiration(token)) {
                    log.info("access token 만료");
                    throw new GreenMarketException(ErrorCode.ACCESS_TOKEN_EXPIRED);
                }

                // claim 을 받아와 정보 추출
                loginId = (String) jwtProvider.get(token).get("email");

                PrincipalDetails principalDetails = customUserDetailsService.loadUserByUsername(loginId);

                // 인증 정보 생성
                Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null,
                    principalDetails.getAuthorities());
                // SecurityContextHolder에 인증 정보 설정
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("회원 인증 완료");
            }

            filterChain.doFilter(request, response);
        } catch (GreenMarketException e) {
            // response 세팅
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8"); // JSON 응답을 UTF-8로 설정
            response.setContentType(APPLICATION_JSON_VALUE);

            // 만료된 토큰 에러라면
            if (e.getMessage().equalsIgnoreCase("토큰이 만료되었습니다.")) {
                writeErrorLogs("EXPIRED_ACCESS_TOKEN", e.getMessage(), e.getStackTrace());
                // 응값 코드 세팅
                response.setStatus(e.getStatus().value());
                response.getWriter()
                    .write(objectMapper.writeValueAsString(new Response<String>("토큰이 만료되었습니다.")));
                response.getWriter().flush();
                response.getWriter().close();
            }
            // DB 에 없는 유저라면
            else if (e.getMessage().equalsIgnoreCase("존재하지 않는 회원입니다.")) {
                writeErrorLogs("CANNOT_FOUND_USER", e.getMessage(), e.getStackTrace());
                response.setStatus(e.getStatus().value());
                response.getWriter().write(objectMapper.writeValueAsString(new Response<String>("존재하지 않는 회원입니다.")));
                response.getWriter().flush();
                response.getWriter().close();
            }
        }
    }

    // 에러를 log 로 기록 하기 위한 함수
    private void writeErrorLogs(String exception, String message, StackTraceElement[] stackTraceElements) {
        log.error("**** " + exception + " ****");
        log.error("**** error message : " + message);
        log.error("**** stack trace : " + Arrays.toString(stackTraceElements));
    }
}