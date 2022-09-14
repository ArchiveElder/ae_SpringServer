package com.ae.ae_SpringServer.api;

import com.ae.ae_SpringServer.domain.User;
import com.ae.ae_SpringServer.dto.response.AnalysisDto;
import com.ae.ae_SpringServer.dto.response.AnalysisResponseDto;
import com.ae.ae_SpringServer.jpql.DateAnalysisDto;
import com.ae.ae_SpringServer.service.AnalysisService;
import com.ae.ae_SpringServer.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.valueOf;
import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class AnalysisApiController {
    private final AnalysisService analysisService;
    private final UserService userService;

    //5-1
    @GetMapping("api/analysis")
    public AnalysisResponseDto analysisResponse(@AuthenticationPrincipal String userId) {
        int status = 0;
        int ratioCarb, ratioPro, ratioFat, totalCarb, totalPro, totalFat;
        ratioCarb = ratioPro = ratioFat = totalCarb = totalPro = totalFat = 0;

        User user = userService.findOne(Long.valueOf(userId));
        List<DateAnalysisDto> findRecords = analysisService.findRecords(Long.valueOf(userId));
        //받아온 기록이 7개일 경우 : 정상로직 : status = 1
        if(findRecords.size() == 7) {
           status = 1;
           List<AnalysisDto> collect = new ArrayList<>();

           for(DateAnalysisDto dateAnalysisDto : findRecords) {
               totalCarb += dateAnalysisDto.getSumCarb();
               totalPro += dateAnalysisDto.getSumPro();
               totalFat += dateAnalysisDto.getSumFat();
               collect.add(new AnalysisDto(dateAnalysisDto.getDate().substring(5,10), dateAnalysisDto.getSumCal().intValue()));
           }
           int sum = totalCarb + totalPro + totalFat;
           ratioCarb = totalCarb * 100 / sum;
           ratioPro = totalPro * 100 / sum;
           ratioFat = totalFat * 100 / sum;
           return new AnalysisResponseDto(status, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd.")),
                   (int) Math.round(Double.parseDouble(user.getRcal())), (int) Math.round(Double.parseDouble(user.getRcarb())),
                   (int) Math.round(Double.parseDouble(user.getRpro())), (int) Math.round(Double.parseDouble(user.getRfat())),
                   ratioCarb, ratioPro, ratioFat , totalCarb, totalPro, totalFat, collect);
       }
       //비정상 로직 status = 0
       else { return new AnalysisResponseDto(status,LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd.")),
                (int) Math.round(Double.parseDouble(user.getRcal())), (int) Math.round(Double.parseDouble(user.getRcarb())),
                (int) Math.round(Double.parseDouble(user.getRpro())), (int) Math.round(Double.parseDouble(user.getRfat())),
                ratioCarb, ratioPro, ratioFat , totalCarb, totalPro, totalFat, null);}

    }
}
