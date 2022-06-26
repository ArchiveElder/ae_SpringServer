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
public class RecordService {
    private final RecordRepository recordRepository;
    private final UserService userService;

    @Transactional
    public Long record(Record record) {
        recordRepository.save(record);
        return record.getId();
    }

    public List<Record> findDateRecords(Long id, String date) {
        return recordRepository.findDateRecords(id, date);
    }

    public List<Record> findDetailOne(Long id, Long record_id) {
        return recordRepository.findDetaileOne(id, record_id);
    }

}
