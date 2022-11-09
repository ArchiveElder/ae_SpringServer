package com.ae.ae_SpringServer.api;

import com.ae.ae_SpringServer.config.BaseResponse;
import com.ae.ae_SpringServer.domain.User;
import com.ae.ae_SpringServer.dto.response.AnalysisResponseDto;
import com.ae.ae_SpringServer.jpql.DateAnalysisDto;
import com.ae.ae_SpringServer.service.AnalysisService;
import com.ae.ae_SpringServer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.ae.ae_SpringServer.config.BaseResponseStatus.EMPTY_JWT;
import static com.ae.ae_SpringServer.config.BaseResponseStatus.INVALID_JWT;

@RestController
@RequiredArgsConstructor
public class AnalysisApiController {
    private final AnalysisService analysisService;
    private final UserService userService;

    //[GET] 5-1 식단분석
    @GetMapping("v3/analysis")
    public BaseResponse<AnalysisResponseDto> analysisResponse(@AuthenticationPrincipal String userId) {
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
        List<DateAnalysisDto> findWeekRecords = analysisService.findRecordsV3(Long.valueOf(userId));
        return new BaseResponse<>(analysisService.weekAnalysis(findWeekRecords, Long.valueOf(userId)));


    }
}
