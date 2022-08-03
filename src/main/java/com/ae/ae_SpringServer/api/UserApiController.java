package com.ae.ae_SpringServer.api;

import com.ae.ae_SpringServer.config.security.JwtProvider;
import com.ae.ae_SpringServer.domain.User;
import com.ae.ae_SpringServer.dto.KakaoProfile;
import com.ae.ae_SpringServer.dto.SignupRequestDto;
import com.ae.ae_SpringServer.dto.UserSocialLoginRequestDto;
import com.ae.ae_SpringServer.dto.UserUpdateRequestDto;
import com.ae.ae_SpringServer.service.UserService;
import com.nimbusds.jose.shaded.json.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
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

import javax.validation.constraints.NotNull;
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
    public LoginDto loginByKakao(
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
            return new LoginDto(user.get().getId(), jwtProvider.createToken(user.get()), false);
        } else {
            User u = User.createUser(id);
            userService.create(u);
            return new LoginDto(u.getId(), jwtProvider.createToken(u), true);
        }
    }

    @PostMapping("/api/signup")
    public ResponseEntity<?> signup(@AuthenticationPrincipal String userId, @RequestBody SignupRequestDto signupRequestDto) {
        userService.signup(Long.valueOf(userId), signupRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/userinfo")
    public UserInfoDto info(@AuthenticationPrincipal String userId) {
        User user = userService.findOne(Long.valueOf(userId));
        return new UserInfoDto(user.getName(), user.getGender(), user.getAge(), user.getHeight(), user.getWeight(), user.getIcon(), user.getActivity());
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

    @GetMapping("/api/userbadge")
    public Result userBadge() {
        Long id = Long.valueOf(0);
        int num = userService.findBadge(id);
        return new Result(num);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class LoginDto {
        @NotNull
        private Long userId;
        @NotNull
        private String token;
        @NotNull
        private boolean isSignup; // 온보딩 띄워야 하는 여부 (true가 띄워야함)
    }



    @Data
    @AllArgsConstructor
    static class UserInfoDto {
        private String name;
        private int gender;
        private int age;
        private String height;
        private String weight;
        private int icon;
        private int activity;
    }



}
