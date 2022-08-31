package com.ae.ae_SpringServer.config.security;

import com.ae.ae_SpringServer.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@Component
public class JwtProvider {
    @Value("spring.jwt.secretakjhfluwehlfsdfbuawegfdvhsfvawgrywiehsrjfbsauaweiruhawusdhfvwhsvdfalsdfh")
    private String secretKey;

    private Long tokenValidMillisecond = 60 * 60 * 1000L;

    //private final CustomUserDetailsService userDetailsService;
    private static final Map<String, String> SECRET_KEY_SET = Map.of(
            "key1", "aefsdfakjhfluwehlfsdfbuawegfdvhsfvawgrywiehsrjfbsauaweiruhawusdhfvwhsvdfalsdfh",
            "key2", "werwsdfzxchaebbiakjhfluwehlfsdfbuawegfdvhsfvawgrywiehsrjfbsauaweiruhawusdhfvwhsvdfalsdfh",
            "key3", "werwscbdcvcakjhfluwehlfsdfbuawegfdvhsfvawgrywiehsrjfbsauaweiruhawusdhfvwhsvdfalsdfh"
    );
    private static final String[] KID_SET = SECRET_KEY_SET.keySet().toArray(new String[0]);
    private static Random randomIndex = new Random();
    private static Date expiryDate = Date.from(Instant.now().plus(90, ChronoUnit.DAYS));


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

    public static String createAccessToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getId().toString());
        Date now = new Date();
        Pair<String, Key> key = getRandomKey();
        // Token 생성
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(expiryDate) // 토큰 만료 시간 설정
                .setHeaderParam(JwsHeader.KEY_ID, key.getFirst()) //kid
                .signWith(SignatureAlgorithm.HS256, key.getSecond()) // signature
                .compact();
    }
    public static String createRefreshToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getId().toString());
        Date now = new Date();
        Pair<String, Key> key = getRandomKey();
        // Token 생성
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(expiryDate) // 토큰 만료 시간 설정
                .setHeaderParam(JwsHeader.KEY_ID, key.getFirst()) // kid
                .signWith(SignatureAlgorithm.HS256, key.getSecond()) // signature
                .compact();
    }
    public static Pair<String, Key> getRandomKey() {
        String kid = KID_SET[randomIndex.nextInt(KID_SET.length)];
        String secretKey = SECRET_KEY_SET.get(kid);
        return Pair.of(kid, Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)));
    }

    // Token 내용을 뜯어서 id 얻기
    public String validateAndGetUserId(String token) {
        // parseClaimsJws 메서드가 base64로 디코딩 및 파싱
        // 헤더와 페이로드를 setsigninKey로 넘어온 시크릿을 이용해 서명한 후 token의 서명과 비교
        // 위조되지 않았다면 페이로드(Claims) 리턴, 위조라면 예외를 날림
        // 그중 우리는 userId가 필요하므로 getBody를 부른다.
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
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
