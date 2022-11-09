package com.ae.ae_SpringServer.config.security;

import com.ae.ae_SpringServer.domain.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Component

public class JwtProvider {
    @Value("${jwt.secret-key}")
    private String secretKey;

    private Long tokenValidMillisecond = 60 * 60 * 1000L;

    //private final CustomUserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // Jwt 생성
    public String createToken(User user) {
        // 기한은 지금부터 90일로 설정
        Date expiryDate = Date.from(Instant.now().plus(90, ChronoUnit.DAYS));

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .setSubject(user.getId().toString())
                .setIssuer("app")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .compact();
    }
    // Token 내용을 뜯어서 id 얻기
    public String validateAndGetUserId(String token) {
        // parseClaimsJws 메서드가 base64로 디코딩 및 파싱
        // 헤더와 페이로드를 setsigninKey로 넘어온 시크릿을 이용해 서명한 후 token의 서명과 비교
        // 위조되지 않았다면 페이로드(Claims) 리턴, 위조라면 예외를 날림
        // 그중 우리는 userId가 필요하므로 getBody를 부른다.
        try{
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("*** token 내용에 에러가 있음 -- JwtProvide");
            return "INVALID JWT";
        }

    }


    /*
    // Jwt로 인증정보를 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // jwt에서 회원 구분 Pk 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // HTTP Request의 Header에서 Token Parsing -> "X-AUTH-TOKEN: jwt"
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }
       // jwt의 유효성 및 만료일자 확인
    public boolean validationToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date()); // 만료날짜가 현재보다 이전이면 false
        } catch (Exception e) {
            return false;
        }
    }

     */
}
