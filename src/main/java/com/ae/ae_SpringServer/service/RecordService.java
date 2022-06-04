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

    public List<Record> findRecords(Long id) {
        return recordRepository.findUserRecord(id);
    }

    /* 2차때 구현
    public List<Record> findRecordSome(Long id) {
        return recordRepository.findUserRecordSome(id);
    }
     */

    @Transactional
    public Long record(Record record) {
        recordRepository.save(record);
        //userService.updateBadge(record.getUser().getId());
        //userService.updateSome(record.getUser().getId(), record);
        return record.getId();
    }

    public List<Record> findDateRecords(Long id, String date) {
        return recordRepository.findDateRecords(id, date);
    }
}
