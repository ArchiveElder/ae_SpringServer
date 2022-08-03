package com.ae.ae_SpringServer.service;

import com.ae.ae_SpringServer.domain.Record;
import com.ae.ae_SpringServer.domain.User;
import com.ae.ae_SpringServer.repository.RecordRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebAppConfiguration
@SpringBootTest
@Transactional
public class RecordServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    RecordService recordService;
    @Autowired
    RecordRepository recordRepository;

    @Test
    public void 식단등록() {
        // given
        User user = new User();
        user.setName("홍길동");
        user.setGender(0);
        user.setAge(23);
        user.setHeight("170");
        user.setWeight("70");
        user.setIcon(1);
        user.setActivity(40);
        em.persist(user);

        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd."));

        Long id = null;
        Record record = Record.createRecord("https://ae-s3-17.s3.ap-northeast-2.amazonaws.com/static/faca0e6c-0cb6-4b2d-b54c-c8a03f1b9a7792213+bytes.jpeg"
                , "김치찌개", date, "153", "13", "23", "4",
                "2022.08.01.", "22:00", 300D, 0, user);


        // when
        id = recordService.record(record);

        // then
        Record getRecord = recordRepository.findDetaileOne(user.getId(), id).get(0);

        assertEquals(id, getRecord.getId());
    }

    @Test
    public void 식단상세조회() {
        // given
        User user = new User();
        user.setName("홍길동");
        user.setGender(0);
        user.setAge(23);
        user.setHeight("170");
        user.setWeight("70");
        user.setIcon(1);
        user.setActivity(40);
        em.persist(user);

        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd."));

        Long id = null;
        Record record = Record.createRecord("https://ae-s3-17.s3.ap-northeast-2.amazonaws.com/static/faca0e6c-0cb6-4b2d-b54c-c8a03f1b9a7792213+bytes.jpeg"
                , "김치찌개", date, "153", "13", "23", "4",
                "2022.08.01.", "22:00", 300D, 0, user);

        id = recordService.record(record);

        // when
        Record getRecord = recordRepository.findDetaileOne(user.getId(), id).get(0);

        // then
        assertEquals(id, getRecord.getId());
    }

}
