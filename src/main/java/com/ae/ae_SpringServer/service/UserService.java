package com.ae.ae_SpringServer.service;

import com.ae.ae_SpringServer.config.security.JwtProperties;
import com.ae.ae_SpringServer.config.security.JwtProvider;
import com.ae.ae_SpringServer.domain.User;
import com.ae.ae_SpringServer.dto.request.SignupRequestDto;
import com.ae.ae_SpringServer.dto.request.UserSocialLoginRequestDto;
import com.ae.ae_SpringServer.dto.request.UserUpdateRequestDto;
import com.ae.ae_SpringServer.dto.response.AppleLoginResponse;
import com.ae.ae_SpringServer.dto.response.LoginResponseDto;
import com.ae.ae_SpringServer.exception.AeException;
import com.ae.ae_SpringServer.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static com.ae.ae_SpringServer.exception.CodeAndMessage.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private static final String APPLE_AUTH = "https://appleid.apple.com/auth/keys";


    public User findOne(Long id) {
        return userRepository.findOne(id);
    }

    public void create(User user) {
        userRepository.save(user);
    }

    public Optional<User> findByKakaoId(String kakao) {
        return userRepository.findByKakao(kakao);
    }

    public void signup(Long id, SignupRequestDto dto) {
        userRepository.signup(id, dto);
    }

    public void update(Long id, UserUpdateRequestDto dto) {
        userRepository.update(id, dto);
    }

    public LoginResponseDto login(UserSocialLoginRequestDto socialLoginRequestDto) {
        String appleId = getSocialId(socialLoginRequestDto.getAccessToken());
        Optional<User> user = userRepository.findByApple(appleId);
        boolean isEmpty = user.isEmpty();
        // 로그인
        if(!isEmpty) {
            //Pair<String, String> tokens = getTokens(user.get());
            //return new AppleLoginResponse(user.get().getId(), tokens.getFirst(), tokens.getSecond(), jwtProvider.createToken(user.get()), true);
            return new LoginResponseDto(user.get().getId(),jwtProvider.createToken(user.get()), true);
        }
        // 회원가입
        else {
            User u = User.createAppleUser(appleId);
            create(u);
            //Pair<String, String> tokens = getTokens(u);
            //return new AppleLoginResponse(u.getId(), tokens.getFirst(), tokens.getSecond(), jwtProvider.createToken(u), false);
            return new LoginResponseDto(u.getId(), jwtProvider.createToken(u), false);
        }


    }

    // 엑세스 토큰으로 apple 고유 ID "sub" 추출
    private String getSocialId(String accessToken) {
        return verifyAppleToken(accessToken);
    }

    // 애플 엑세스 토큰 유효성 검증
    private String verifyAppleToken(String token) {
        String[] decodeArray = token.split("\\.");
        String headerStr = new String(Base64.getDecoder().decode(decodeArray[0]));
        String payloadStr = new String(Base64.getDecoder().decode(decodeArray[1]));
        try {
            JSONParser parser = new JSONParser();
            JSONObject payload = (JSONObject) parser.parse(payloadStr);
            return String.valueOf(payload.get("sub"));
        } catch (ParseException e) {
            throw new AeException(INVALID_APPLE_TOKEN);
        }
    }
    /*
    // createAccessToken, createRefreshToken 발급
    private Pair<String, String> getTokens(User user) {
        String accessToken = createAccessToken(user);
        String refreshToken = createRefreshToken(user);


        return Pair.of(accessToken, refreshToken);
    }
    // 애플 공개키 조회
    private JSONObject getApplePublicKey(String headerStr) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            URI uri = URI.create(APPLE_AUTH);
            ResponseEntity<JSONObject> apiResponse = restTemplate.getForEntity(uri, JSONObject.class);
            JSONObject responseBody = apiResponse.getBody();
            ArrayList<JSONObject> keyArray = (ArrayList<JSONObject>) responseBody.get("keys");

            JSONParser parser = new JSONParser();
            JSONObject header = (JSONObject) parser.parse(headerStr);
            JSONObject availableKey = null;

            for (int i=0; i<keyArray.size(); i++) {
                JSONObject key = new JSONObject(keyArray.get(i));
                if (key.get("kid").equals(header.get("kid")) &&
                        key.get("alg").equals(header.get("alg"))) {
                    availableKey = key;
                }
            }
            if (ObjectUtils.isEmpty(availableKey))
                throw new AeException(FAILED_TO_FIND_AVAILABLE_RSA);
            return availableKey;

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new AeException(INVALID_APPLE_ACCESS);
        } catch (ParseException e) {
            throw new AeException(INVALID_APPLE_TOKEN);
        }
    }*/



}
