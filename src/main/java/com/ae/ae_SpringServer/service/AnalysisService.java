package com.ae.ae_SpringServer.service;

import com.ae.ae_SpringServer.domain.Record;
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
    public List<Record> findRecords(Long id) { return  recordRepository.analysis(id); }


}
