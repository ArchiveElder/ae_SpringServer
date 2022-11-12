package com.ae.ae_SpringServer.service;

import com.ae.ae_SpringServer.config.BaseResponse;
import com.ae.ae_SpringServer.config.security.JwtProperties;
import com.ae.ae_SpringServer.config.security.JwtProvider;
import com.ae.ae_SpringServer.domain.User;
import com.ae.ae_SpringServer.dto.request.SignupRequestDto;
import com.ae.ae_SpringServer.dto.request.UserSocialLoginRequestDto;
import com.ae.ae_SpringServer.dto.request.UserUpdateRequestDto;
import com.ae.ae_SpringServer.dto.request.v3.SignupRequestDtoV3;
import com.ae.ae_SpringServer.dto.request.v3.UserUpdateRequestDtoV3;
import com.ae.ae_SpringServer.dto.response.AppleLoginResponse;
import com.ae.ae_SpringServer.dto.response.LoginResponseDto;
import com.ae.ae_SpringServer.exception.AeException;
import com.ae.ae_SpringServer.repository.UserRepository;

import com.ae.ae_SpringServer.repository.UserRepositoryV3;
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
    private final UserRepositoryV3 userRepositoryV3;
    private final JwtProvider jwtProvider;
    private static final String APPLE_AUTH = "https://appleid.apple.com/auth/keys";


    public User findOne(Long id) {
        return userRepository.findOne(id);
    }

    public Optional<User> findById(Long id) {
        return userRepositoryV3.findById(id);
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
    public void signupNickname(Long id, SignupRequestDtoV3 dto) {
        userRepository.signupNickname(id, dto);
    }

    public void update(Long id, UserUpdateRequestDto dto) {
        userRepository.update(id, dto);
    }

    public void delete(Long id) {
        userRepository.delete(id);
    }

    public LoginResponseDto login(UserSocialLoginRequestDto socialLoginRequestDto) {
        String appleId = getSocialId(socialLoginRequestDto.getAccessToken());
        Optional<User> user = userRepository.findByApple(appleId);
        boolean isEmpty = user.isEmpty();
        // 로그인
        if(!isEmpty) {
            // 로그인 후 온보딩이 아직 입력 안된 상태
            if(user.get().getHeight() == null || user.get().getWeight() == null) {
                return new LoginResponseDto(user.get().getId(), jwtProvider.createToken(user.get()), true);
            }
            return new LoginResponseDto(user.get().getId(),jwtProvider.createToken(user.get()), false);
        }
        // 회원가입
        else {
            User u = User.createAppleUser(appleId);
            create(u);
            return new LoginResponseDto(u.getId(), jwtProvider.createToken(u), true);
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

    public Long nicknameCheck(String nickname) {
        return userRepository.nicknameCheck(nickname);
    }


    public void updateV3(Long id, UserUpdateRequestDtoV3 dto) {
        userRepository.updateV3(id, dto);
    }
}
