package com.ae.ae_SpringServer.service;

import com.ae.ae_SpringServer.domain.Suggestion;
import com.ae.ae_SpringServer.dto.ProblemsDto;
import com.ae.ae_SpringServer.dto.SuggestionsDto;
import com.ae.ae_SpringServer.dto.response.AnalysisDto;
import com.ae.ae_SpringServer.dto.response.AnalysisResponseDto;
import com.ae.ae_SpringServer.jpql.DateAnalysisDto;
import com.ae.ae_SpringServer.jpql.DateAnalysisDtoV2;
import com.ae.ae_SpringServer.repository.RecordRepository;
import com.ae.ae_SpringServer.repository.SuggestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnalysisService {
    private final RecordRepository recordRepository;
    private final UserService userService;
    private final SuggestionRepository suggestionRepository;

    public List<DateAnalysisDtoV2> findRecords(Long id) {
        return recordRepository.analysisDate(id);
    }
    public List<DateAnalysisDto> findRecordsV3(Long id) {
        return recordRepository.analysisDateV3(id);
    }

    public AnalysisResponseDto weekAnalysis(List<DateAnalysisDto> weekRecords, Long userId) {
        int status = 0;
        int ratioCarb, ratioPro, ratioFat, totalCarb, totalPro, totalFat;
        ratioCarb = ratioPro = ratioFat = totalCarb = totalPro = totalFat = 0;
        //받아온 기록이 7개일 경우 : 정상로직 : status = 1
        if(weekRecords.size() == 7) {
            // 식단에 problem check
            List<ProblemsDto> problemsList = problemCheck(weekRecords, userId);

            // 문제 식이 습관의 제안 리스트
            List<SuggestionsDto> suggestionList = solSuggest(problemsList);


            status = 1;
            List<AnalysisDto> collect = new ArrayList<>();

            for(DateAnalysisDto dateAnalysisDto : weekRecords) {
                totalCarb += dateAnalysisDto.getSumCarb();
                totalPro += dateAnalysisDto.getSumPro();
                totalFat += dateAnalysisDto.getSumFat();
                collect.add(new AnalysisDto(dateAnalysisDto.getDate(), dateAnalysisDto.getSumCal().intValue()));
            }
            int sum = totalCarb + totalPro + totalFat;
            ratioCarb = totalCarb * 100 / sum;
            ratioPro = totalPro * 100 / sum;
            ratioFat = totalFat * 100 / sum;
            return new AnalysisResponseDto(status, ratioCarb, ratioPro, ratioFat , totalCarb, totalPro, totalFat, problemsList, suggestionList, collect);
        }
        //비정상 로직 status = 0
        else { return new AnalysisResponseDto(status, ratioCarb, ratioPro, ratioFat , totalCarb, totalPro, totalFat, null, null, null);}
    }
    private List<SuggestionsDto> solSuggest(List<ProblemsDto> problemsList) {
        List<SuggestionsDto> suggestionList = new ArrayList<>();

        for(ProblemsDto problemsDto : problemsList) {
            List<Suggestion> suggestions = suggestionRepository.getSuggestions(Long.valueOf(problemsDto.getProblemId()));
            for(Suggestion suggestion : suggestions) suggestionList.add(new SuggestionsDto(suggestion.getProblemId(), suggestion.getFoodUrl(), suggestion.getFoodName()));
        }
        return suggestionList;
    }

    public List<ProblemsDto> problemCheck(List<DateAnalysisDto> weekRecords, Long userId) {
        Double rcal = Double.parseDouble(userService.findOne(userId).getRcal());    //권장칼로리
        int weight = Integer.parseInt(userService.findOne(userId).getWeight());     //체중
        int highCal, lowCal, highCarb, lowCarb, highPro, lowPro, highFat, lowFat;
        highCal = lowCal = highCarb = lowCarb = highPro = lowPro = highFat = lowFat = 0;
        //문제되는 섭취를 4일 이상했을 시 -> problem
        for(DateAnalysisDto dayRecord : weekRecords) {
            if(dayRecord.getSumCal() >= rcal*1.2) highCal++;     //고칼로리 식이 판단
            else if(dayRecord.getSumCal() <= rcal*0.8) lowCal++;     //저칼로리 식이 판단

            if(dayRecord.getSumCarb()/ dayRecord.getSumAmount() > 0.7 && dayRecord.getSumFat()/dayRecord.getSumAmount() <0.15) highCarb++; //고탄수화물 식이 판단
            else if(dayRecord.getSumCarb()/dayRecord.getSumAmount() <0.45) lowCarb++;    //저탄수화물 식이 판단

            if(dayRecord.getSumPro() >= 1.5*weight) highPro++;  // 고단백 식이판단
            else if(dayRecord.getSumPro() <= 40) lowPro++;      //저단백 식이판단

            if(dayRecord.getSumCarb()/dayRecord.getSumAmount() < 0.6 && dayRecord.getSumFat()/dayRecord.getSumAmount() >= 0.25) highFat++; //고지방 식이 판단
            else if(dayRecord.getSumFat()/dayRecord.getSumAmount() < 0.3) lowFat++;      //저지방 식이 판단

        }
        List<ProblemsDto> problemsDtos = new ArrayList<>(); //문제가 되는 식이습관 리스트
        if(highCal >=4) problemsDtos.add(new ProblemsDto(1, highCal));
        if(lowCal >= 4) problemsDtos.add(new ProblemsDto(2, lowCal));
        if(highCarb >= 4) problemsDtos.add(new ProblemsDto(3, highCarb));
        if(lowCarb >= 4) problemsDtos.add(new ProblemsDto(4, lowCarb));
        if(highPro >= 4) problemsDtos.add(new ProblemsDto(5, highPro));
        if(lowPro >= 4) problemsDtos.add(new ProblemsDto(6, lowPro));
        if(highFat >= 4) problemsDtos.add(new ProblemsDto(7, highFat));
        if(lowFat >= 4) problemsDtos.add(new ProblemsDto(8, lowFat));

        return problemsDtos;

    }


}
