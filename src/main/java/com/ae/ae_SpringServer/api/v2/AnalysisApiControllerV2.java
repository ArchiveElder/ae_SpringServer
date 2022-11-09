package com.ae.ae_SpringServer.api.v2;

import com.ae.ae_SpringServer.config.BaseResponse;
import com.ae.ae_SpringServer.domain.User;
import com.ae.ae_SpringServer.dto.response.AnalysisDto;
import com.ae.ae_SpringServer.dto.response.v2.AnalysisResponseDtoV2;
import com.ae.ae_SpringServer.jpql.DateAnalysisDtoV2;
import com.ae.ae_SpringServer.service.AnalysisService;
import com.ae.ae_SpringServer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.ae.ae_SpringServer.config.BaseResponseStatus.EMPTY_JWT;
import static com.ae.ae_SpringServer.config.BaseResponseStatus.INVALID_JWT;

@RestController
@RequiredArgsConstructor
public class AnalysisApiControllerV2 {

    private final AnalysisService analysisService;
    private final UserService userService;
    //[GET] 5-1 식단분석
    @GetMapping("api/v2/analysis")
    public BaseResponse<AnalysisResponseDtoV2> analysisResponse(@AuthenticationPrincipal String userId) {
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
        int status = 0;
        int ratioCarb, ratioPro, ratioFat, totalCarb, totalPro, totalFat;
        ratioCarb = ratioPro = ratioFat = totalCarb = totalPro = totalFat = 0;

        List<DateAnalysisDtoV2> findRecords = analysisService.findRecords(Long.valueOf(userId));
        //받아온 기록이 7개일 경우 : 정상로직 : status = 1
        if(findRecords.size() == 7) {
            status = 1;
            List<AnalysisDto> collect = new ArrayList<>();

            for(DateAnalysisDtoV2 dateAnalysisDtoV2 : findRecords) {
                totalCarb += dateAnalysisDtoV2.getSumCarb();
                totalPro += dateAnalysisDtoV2.getSumPro();
                totalFat += dateAnalysisDtoV2.getSumFat();
                collect.add(new AnalysisDto(dateAnalysisDtoV2.getDate().substring(5,10), dateAnalysisDtoV2.getSumCal().intValue()));
            }
            int sum = totalCarb + totalPro + totalFat;
            ratioCarb = totalCarb * 100 / sum;
            ratioPro = totalPro * 100 / sum;
            ratioFat = totalFat * 100 / sum;
            return new BaseResponse<>(new AnalysisResponseDtoV2(status, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd.")),
                    String.valueOf((int) Math.round(Double.parseDouble(user.getRcal()))), String.valueOf((int) Math.round(Double.parseDouble(user.getRcarb()))),
                    String.valueOf((int) Math.round(Double.parseDouble(user.getRpro()))), String.valueOf((int) Math.round(Double.parseDouble(user.getRfat()))),
                    ratioCarb, ratioPro, ratioFat , totalCarb, totalPro, totalFat, collect));

        }
        //비정상 로직 status = 0
        else { return new BaseResponse<>(new AnalysisResponseDtoV2(status,LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd.")),
                String.valueOf((int) Math.round(Double.parseDouble(user.getRcal()))), String.valueOf((int) Math.round(Double.parseDouble(user.getRcarb()))),
                String.valueOf((int) Math.round(Double.parseDouble(user.getRpro()))), String.valueOf((int) Math.round(Double.parseDouble(user.getRfat()))),
                ratioCarb, ratioPro, ratioFat , totalCarb, totalPro, totalFat, null));}

    }
}
