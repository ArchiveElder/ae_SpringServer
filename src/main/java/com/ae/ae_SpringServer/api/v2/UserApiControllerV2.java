package com.ae.ae_SpringServer.api.v2;

import com.ae.ae_SpringServer.config.BaseResponse;
import com.ae.ae_SpringServer.config.security.JwtProvider;
import com.ae.ae_SpringServer.domain.User;
import com.ae.ae_SpringServer.dto.request.SignupRequestDto;
import com.ae.ae_SpringServer.dto.request.UserUpdateRequestDto;
import com.ae.ae_SpringServer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.ae.ae_SpringServer.config.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
public class UserApiControllerV2 {
    private final UserService userService;
    private final JwtProvider jwtProvider;

    // [POST] 3-3  회원 등록 (version 2 )
    @PostMapping("/api/v2/signup")
    public BaseResponse<String> signup(@AuthenticationPrincipal String userId, @RequestBody SignupRequestDto signupRequestDto) {
        if(userId.equals("INVALID JWT")){
            return new BaseResponse<>(INVALID_JWT);
        }
        if(userId == null) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        User user = userService.findOne(Long.valueOf(userId));
        if (user == null) {
            return new BaseResponse<>(INVALID_JWT);
        }
        if(signupRequestDto.getName().isEmpty() || signupRequestDto.getName().equals("")) {
            return new BaseResponse<>(POST_USER_NO_NAME);
        }
        if(signupRequestDto.getName().length() > 45) {
            return new BaseResponse<>(POST_USER_LONG_NAME);
        }
        if(signupRequestDto.getAge() < 1) {
            return new BaseResponse<>(POST_USER_MINUS_AGE);
        }
        if(signupRequestDto.getGender() != 0 && signupRequestDto.getGender() != 1) {
            return new BaseResponse<>(POST_USER_INVALID_GENDER);
        }

        if(signupRequestDto.getHeight().isEmpty() || signupRequestDto.getHeight().equals("")) {
            return new BaseResponse<>(POST_USER_NO_HEIGHT);
        }

        if(Integer.parseInt(signupRequestDto.getHeight()) < 0) {
            return new BaseResponse<>(POST_USER_MINUS_HEIGHT);
        }

        if(signupRequestDto.getWeight().isEmpty() || signupRequestDto.getWeight().equals("")) {
            return new BaseResponse<>(POST_USER_NO_WEIGHT);
        }

        if(Integer.parseInt(signupRequestDto.getWeight()) < 0) {
            return new BaseResponse<>(POST_USER_MINUS_WEIGHT);
        }
        if(signupRequestDto.getActivity() != 25 && signupRequestDto.getActivity() != 33 && Integer.valueOf(signupRequestDto.getActivity()) != 40) {
            return new BaseResponse<>(POST_USER_INVALID_ACTIVITY);
        }
        userService.signup(Long.valueOf(userId), signupRequestDto);
        return new BaseResponse<>(userId + "번  회원 등록되었습니다");
    }
    // [PUT] 3-2 회원 정보 수정 for version2
    @PutMapping("/api/v2/userupdate")
    public BaseResponse<String>  update(@AuthenticationPrincipal String userId, @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        if(userId.equals("INVALID JWT")){
            return new BaseResponse<>(INVALID_JWT);
        }
        if(userId == null) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        User user = userService.findOne(Long.valueOf(userId));
        if (user == null) {
            return new BaseResponse<>(INVALID_JWT);
        }
        if(userUpdateRequestDto.getAge() < 1) {
            return new BaseResponse<>(PUT_USER_MINUS_AGE);
        }

        if(userUpdateRequestDto.getHeight().isEmpty() || userUpdateRequestDto.getHeight().equals("")) {
            return new BaseResponse<>(PUT_USER_NO_HEIGHT);
        }

        if(Integer.parseInt(userUpdateRequestDto.getHeight()) < 0) {
            return new BaseResponse<>(PUT_USER_MINUS_HEIGHT);
        }

        if(userUpdateRequestDto.getWeight().isEmpty() || userUpdateRequestDto.getWeight().equals("")) {
            return new BaseResponse<>(PUT_USER_NO_WEIGHT);
        }

        if(Integer.parseInt(userUpdateRequestDto.getWeight()) < 0) {
            return new BaseResponse<>(PUT_USER_MINUS_WEIGHT);
        }
        if(Integer.valueOf(userUpdateRequestDto.getActivity()) != 25 && Integer.valueOf(userUpdateRequestDto.getActivity()) != 33 && Integer.valueOf(userUpdateRequestDto.getActivity()) != 40) {
            return new BaseResponse<>(PUT_USER_INVALID_ACTIVITY);
        }

        userService.update(Long.valueOf(userId), userUpdateRequestDto);
        return new BaseResponse<>(userId + "번  회원 정보 수정되었습니다");
    }
}
