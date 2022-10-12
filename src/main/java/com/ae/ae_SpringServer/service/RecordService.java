package com.ae.ae_SpringServer.service;

import com.ae.ae_SpringServer.domain.Record;
import com.ae.ae_SpringServer.domain.User;
import com.ae.ae_SpringServer.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class RecordService {
    private final RecordRepository recordRepository;
    private final UserService userService;

    @Transactional
    public Long record(Record record) {
        recordRepository.save(record);
        return record.getId();
    }

    @Transactional
    public Long update(Long recordId, String imgUrl, String text, String date, String calory, String carb, String protein, String fat,
                       String rdate, String rtime, double amount, int meal, User user) {
        return recordRepository.update(recordId, imgUrl, text, date, calory, carb, protein, fat,
                rdate, rtime, amount, meal, user);
    }

    public void delete(Long userId, Long recordId) {
        recordRepository.delete(userId, recordId);
    }

    public List<Record> findDateRecords(Long id, String date) {
        return recordRepository.findDateRecords(id, date);
    }

    public List<Record> findDetailOne(Long id, Long record_id) {
        return recordRepository.findDetaileOne(id, record_id);
    }

}
