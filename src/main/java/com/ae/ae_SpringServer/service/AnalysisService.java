package com.ae.ae_SpringServer.service;

import com.ae.ae_SpringServer.jpql.DateAnalysisDto;
import com.ae.ae_SpringServer.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnalysisService {
    private final RecordRepository recordRepository;
    public List<DateAnalysisDto> findRecords(Long id) {
        //현재날짜 제외하고 7개 날짜 검색하는 쿼리 날리는 코드 : 7개 날짜들 반환
        //List dateRecords= recordRepository.analysisDates(id, 0, 7);
        //Pageable paging = PageRequest.of(0,7);

        //Page<DateAnalysisDto> dateRecords = recordRepository.analysisDates(id, paging);
        //List<DateAnalysisDto> dateRecords = recordRepository.analysisDates(id, PageRequest.of(0, 7));
        return recordRepository.analysisDate(id);
//        for(DateAnalysisDto dateAnalysisDto : dateRecords) {
//            String date = dateAnalysisDto.getDate();
//        }

        //return recordRepository.analysis((String) frst, (String) lst);
    }


}
