package com.ae.ae_SpringServer.api;

import com.ae.ae_SpringServer.domain.Record;
import com.ae.ae_SpringServer.jpql.DateAnalysisDto;
import com.ae.ae_SpringServer.service.AnalysisService;
import com.ae.ae_SpringServer.service.RecordService;
import com.ae.ae_SpringServer.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public AnalysisResponse analysisResponse() {
        Long id = Long.valueOf(3);
        int ratioCarb, ratioPro, ratioFat, totalCarb, totalPro, totalFat;
        ratioCarb = ratioPro = ratioFat = totalCarb = totalPro = totalFat = 0;

        List<DateAnalysisDto> findRecords = analysisService.findRecords(id);

        List<AnalysisDto> collect = findRecords.stream()
                .map(m -> new AnalysisDto(m.getDate(), Double.valueOf(m.getSumCal())))
                .collect(toList());


        //sumCarb, sumPro, sumFat, ratio 관련 처리해주고

        return new AnalysisResponse(ratioCarb, ratioPro, ratioFat , totalCarb, totalPro, totalFat, collect);


    }

    @Data
    @AllArgsConstructor
    private class AnalysisResponse {    //7일간 섭취 영양소 비율, 총량, [날짜별 총칼로리]
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
        private Double totalCal;
    }
}
