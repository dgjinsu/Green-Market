package user_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${token.expiration_time}")
    private Long expirationTime;
    private final Key key;

    @Autowired
    public JwtTokenProvider(@Value("${token.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(String email) {
        Map<String, Object> claim = new HashMap<>();
        // 토큰에 들어갈 정보 세팅
        claim.put("email", email); // 이메일
        return createJwt("ACCESS_TOKEN", expirationTime, claim);
    }

    public String createJwt(String subject, Long expiration, Map<String, Object> claim) {
        JwtBuilder jwtBuilder = Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setSubject(subject)
            .setIssuedAt(new Date())
            .signWith(key, SignatureAlgorithm.HS256);

        // claim 세팅
        if (claim != null) {
            jwtBuilder.setClaims(claim);
        }

        // 만료 기한 설정
        if (expiration != null) {
            jwtBuilder.setExpiration(new Date(new Date().getTime() + expiration));
        }

        return jwtBuilder.compact();
    }
}
