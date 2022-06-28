package com.ae.ae_SpringServer.api;

import com.ae.ae_SpringServer.domain.Record;
import com.ae.ae_SpringServer.service.AnalysisService;
import com.ae.ae_SpringServer.service.RecordService;
import com.ae.ae_SpringServer.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        List<Record> findRecords = analysisService.findRecords(id);
        List<AnalysisDto> collect = findRecords.stream()
                .map(m -> new AnalysisDto(m.getId(),m.getDate(), m.getCal()))
                .collect(toList());
        return new AnalysisResponse(collect);


    }

    @Data
    @AllArgsConstructor
    private class AnalysisResponse {
//        private int ratioCarb;
//        private int ratioPro;
//        private int ratioFat;
//        private int totalCarb;
//        private int totalPro;
//        private int totalFat;
        private List<AnalysisDto> analysisDtos;
    }

    @Data
    @AllArgsConstructor
    static class AnalysisDto {
        private Long id;
        private String date;
        //private int totalCal;
        private String cal;
    }
}
