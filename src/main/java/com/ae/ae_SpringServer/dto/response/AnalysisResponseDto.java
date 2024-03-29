package com.ae.ae_SpringServer.dto.response;


import com.ae.ae_SpringServer.dto.ProblemsDto;
import com.ae.ae_SpringServer.dto.SuggestionsDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
public class AnalysisResponseDto {
    @NotNull
    private int status;         //정상 로직이면 1, 비정상이면 0
    private int ratioCarb;
    private int ratioPro;
    private int ratioFat;
    private int totalCarb;
    private int totalPro;
    private int totalFat;
    private List<ProblemsDto> problemsDtoList;
    private List<SuggestionsDto> suggestionsDtoList;
    private List<AnalysisDto> analysisDtos;
}

