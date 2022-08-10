package com.ae.ae_SpringServer.service;

import com.ae.ae_SpringServer.domain.User;
import com.ae.ae_SpringServer.dto.*;
import com.ae.ae_SpringServer.dto.request.CalcRequestDto;
import com.ae.ae_SpringServer.dto.request.UserUpdateRequestDto;
import com.ae.ae_SpringServer.utils.CalcNutrients;
import com.nimbusds.jose.shaded.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@WebAppConfiguration
@SpringBootTest
@Transactional
public class UserServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    UserService userService;

    //3-1
    @Test
    public void 회원정보조회(){
        //given
        //유저 주입 : 가입시 회원 정보
        User user = new User();
        String name = "김박사";
        int sex = 1;
        int age = 30;
        String height = "170";
        String weight = "50";
        int icon =3;
        int activity = 40;
        user.setName(name);
        user.setGender(sex);
        user.setAge(age);
        user.setHeight(height);
        user.setWeight(weight);
        user.setIcon(icon);
        user.setActivity(activity);
        userService.create(user);

        //when 유저id로 조회 시
        User user1 = userService.findOne(user.getId());

        //then 기입한 유저정보와 같아야한다.
        assertEquals(name,user1.getName());
        assertEquals(sex,user1.getGender());
        assertEquals(age,user1.getAge());
        assertEquals(height,user1.getHeight());
        assertEquals(weight,user1.getWeight());
        assertEquals(icon,user1.getIcon());
        assertEquals(activity,user1.getActivity());
    }

    


    //4-1 (3-3보다 우선해서 진행)
    @Test
    public void 카카오로그인() {
        //case 1 : db에 있는 Token의 경우

        //given
        String token = "dgm9YeCAGO5rRxzCESVi9Ids2PDyc2gx5aXs3AY3Cj1ylwAAAYGp5n1p";
        //UserSocialLoginRequestDto requestDto = new UserSocialLoginRequestDto(accessToken);
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

        String kakaoId = String.valueOf(responseBody.get("id"));

        //when : 카카오 로그인을 진행했을 때
        Optional<User> user = userService.findByKakaoId(kakaoId);

        //then
        assertEquals(kakaoId, user.get().getKakao());

    }



}
