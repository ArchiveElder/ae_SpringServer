package com.ae.ae_SpringServer.api;

import com.ae.ae_SpringServer.config.security.JwtProvider;
import com.ae.ae_SpringServer.domain.User;
import com.ae.ae_SpringServer.dto.request.SignupRequestDto;
import com.ae.ae_SpringServer.dto.request.UserSocialLoginRequestDto;
import com.ae.ae_SpringServer.dto.request.UserUpdateRequestDto;
import com.ae.ae_SpringServer.dto.response.AppleLoginResponse;
import com.ae.ae_SpringServer.dto.response.LoginResponseDto;
import com.ae.ae_SpringServer.dto.response.UserInfoResponseDto;
import com.ae.ae_SpringServer.service.UserService;
import com.nimbusds.jose.shaded.json.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;


    // 로그인 시에, kakaoprofile로 받은 정보가 db에 있으면 jwt 토큰 발급(status코드는 온보딩 안띄우게). db에 없으면 new user로 저장시키고 jwt 토큰발급(온보딩 띄우게)
    @PostMapping("/api/login")
    public LoginResponseDto loginByKakao(
            @RequestBody UserSocialLoginRequestDto socialLoginRequestDto) {
        String token = socialLoginRequestDto.getAccessToken();
        // KakaoProfile kakaoProfile = kakaoService.getKakaoProfile(token);

        /*
        if (kakaoProfile.getKakao_account().getEmail() == null) {
            kakaoService.kakaoUnlink(socialSignupRequestDto.getAccessToken());
            throw new CSocialAgreementException();
        }
         */
        RestTemplate restTemplate = new RestTemplate();
        URI uri = URI.create("https://kapi.kakao.com/v2/user/me");
        HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        MultiValueMap<String, Object> pa = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, Object>> restRequest = new HttpEntity<>(pa, headers);
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            public boolean hasError(ClientHttpResponse response) throws IOException {
                HttpStatus statusCode = response.getStatusCode();
                return statusCode.series() == HttpStatus.Series.SERVER_ERROR;
            }
        });
        ResponseEntity<JSONObject> apiResponse = restTemplate.postForEntity(uri, restRequest, JSONObject.class);
        JSONObject responseBody = apiResponse.getBody();

        String id = String.valueOf(responseBody.get("id"));

        Optional<User> user = userService.findByKakaoId(id);
        boolean isEmpty = user.isEmpty();
        System.out.println(isEmpty);
        if(!isEmpty) {
            return new LoginResponseDto(user.get().getId(), jwtProvider.createToken(user.get()), false);
        } else {
            User u = User.createUser(id);
            userService.create(u);
            return new LoginResponseDto(u.getId(), jwtProvider.createToken(u), true);
        }
    }

    //[POST] 4-2 : 애플로그인 api
    @PostMapping("/api/apple-login")
    public LoginResponseDto loginByApple(@RequestBody UserSocialLoginRequestDto socialLoginRequestDto){
        return userService.login(socialLoginRequestDto);

    }

    @PostMapping("/api/signup")
    public ResponseEntity<?> signup(@AuthenticationPrincipal String userId, @RequestBody SignupRequestDto signupRequestDto) {
        userService.signup(Long.valueOf(userId), signupRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/userinfo")
    public UserInfoResponseDto info(@AuthenticationPrincipal String userId) {
        User user = userService.findOne(Long.valueOf(userId));
        return new UserInfoResponseDto(user.getName(), user.getGender(), user.getAge(), user.getHeight(), user.getWeight(), user.getIcon(), user.getActivity());
    }

    @PutMapping("/api/userupdate")
    public ResponseEntity<?> update(@AuthenticationPrincipal String userId, @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        userService.update(Long.valueOf(userId), userUpdateRequestDto);
        return ResponseEntity.ok().build();
    }


    /*
    // 액세스 토큰 만료시 회원 검증 후 리프레쉬 토큰을 검증해서 액세스 토큰과 리프레시 토큰을 재발급함
    @PostMapping("/reissue")
    public SingleResult<TokenDto> reissue(
            @ApiParam(value = "토큰 재발급 요청 DTO", required = true)
            @RequestBody TokenRequestDto tokenRequestDto) {
        return responseService.getSingleResult(signService.reissue(tokenRequestDto));
    }
     */




}
