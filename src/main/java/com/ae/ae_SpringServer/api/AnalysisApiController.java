package com.ae.ae_SpringServer.api;

import com.ae.ae_SpringServer.domain.Record;
import com.ae.ae_SpringServer.jpql.DateAnalysisDto;
import com.ae.ae_SpringServer.service.AnalysisService;
import com.ae.ae_SpringServer.service.RecordService;
import com.ae.ae_SpringServer.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.valueOf;
import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class AnalysisApiController {
    private final RecordService recordService;
    private final UserService userService;
    private final AnalysisService analysisService;

    //5-1
    @GetMapping("api/analysis")
    public AnalysisResponse analysisResponse(@AuthenticationPrincipal String userId) {
        int status = 0;
        int ratioCarb, ratioPro, ratioFat, totalCarb, totalPro, totalFat;
        ratioCarb = ratioPro = ratioFat = totalCarb = totalPro = totalFat = 0;

        List<DateAnalysisDto> findRecords = analysisService.findRecords(Long.valueOf(userId));
        //받아온 기록이 7개일 경우 : 정상로직 : status = 1
        if(findRecords.size() == 7) {
           status = 1;
           List<AnalysisDto> collect = new ArrayList<>();

           for(DateAnalysisDto dateAnalysisDto : findRecords) {
               totalCarb += dateAnalysisDto.getSumCarb();
               totalPro += dateAnalysisDto.getSumPro();
               totalFat += dateAnalysisDto.getSumFat();
               collect.add(new AnalysisDto(dateAnalysisDto.getDate(), dateAnalysisDto.getSumCal().intValue()));
           }
           int sum = totalCarb + totalPro + totalFat;
           ratioCarb = totalCarb * 100 / sum;
           ratioPro = totalPro * 100 / sum;
           ratioFat = totalFat * 100 / sum;
           return new AnalysisResponse(status, ratioCarb, ratioPro, ratioFat , totalCarb, totalPro, totalFat, collect);
       }
       //비정상 로직 status = 0
       else { return new AnalysisResponse(status,ratioCarb, ratioPro, ratioFat , totalCarb, totalPro, totalFat, null);}

    }

    @Data
    @AllArgsConstructor
    private class AnalysisResponse {    //7일간 섭취 영양소 비율, 총량, [날짜별 총칼로리]
        private int status;         //정상 로직이면 1, 비정상이면 0
        private int ratioCarb;
        private int ratioPro;
        private int ratioFat;
        private int totalCarb;
        private int totalPro;
        private int totalFat;
        private List<AnalysisDto> analysisDtos;
    }

    @Data
    @AllArgsConstructor
    static class AnalysisDto {  //날짜별 날짜와 하루총칼로리
        private String date;
        private int totalCal;
    }
}
