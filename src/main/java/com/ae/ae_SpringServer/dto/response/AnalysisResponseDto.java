package com.ae.ae_SpringServer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
@Data
@AllArgsConstructor
public class AnalysisResponseDto {
    @NotNull
    private int status;         //정상 로직이면 1, 비정상이면 0
    private String todayDate;
    private int rcal;
    private int rcarb;
    private int rpro;
    private int rfat;
    private int ratioCarb;
    private int ratioPro;
    private int ratioFat;
    private int totalCarb;
    private int totalPro;
    private int totalFat;
    private List<AnalysisDto> analysisDtos;
}
