package com.ae.ae_SpringServer.service;

import com.ae.ae_SpringServer.domain.Record;
import com.ae.ae_SpringServer.domain.User;
import com.ae.ae_SpringServer.jpql.DateAnalysisDtoV2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebAppConfiguration
@SpringBootTest
@Transactional
public class AnalysisServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    AnalysisService analysisService;
    @Autowired
    RecordService recordService;

    @Test
    public void 식단분석() {
        // given
        int status = 0;
        int ratioCarb, ratioPro, ratioFat, totalCarb, totalPro, totalFat;
        ratioCarb = ratioPro = ratioFat = totalCarb = totalPro = totalFat = 0;

        User user = new User();
        user.setName("홍길동");
        user.setGender(0);
        user.setAge(23);
        user.setHeight("170");
        user.setWeight("70");
        user.setIcon(1);
        user.setActivity(40);
        em.persist(user);

        Long id = null;

        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd."));

        Record record = Record.createRecord("https://ae-s3-17.s3.ap-northeast-2.amazonaws.com/static/faca0e6c-0cb6-4b2d-b54c-c8a03f1b9a7792213+bytes.jpeg"
                , "김치찌개", "2022.08.01.", "153", "13", "23", "4",
                "2022.08.01.", "22:00", 300D, 0, user);
        id = recordService.record(record);
        record = Record.createRecord("https://ae-s3-17.s3.ap-northeast-2.amazonaws.com/static/faca0e6c-0cb6-4b2d-b54c-c8a03f1b9a7792213+bytes.jpeg"
                , "된장찌개", "2022.08.02.", "153", "13", "23", "4",
                "2022.08.02.", "22:00", 300D, 0, user);
        id = recordService.record(record);
        record = Record.createRecord("https://ae-s3-17.s3.ap-northeast-2.amazonaws.com/static/faca0e6c-0cb6-4b2d-b54c-c8a03f1b9a7792213+bytes.jpeg"
                , "부대찌개", "2022.08.03.", "153", "13", "23", "4",
                "2022.08.03.", "22:00", 300D, 0, user);
        id = recordService.record(record);
        record = Record.createRecord("https://ae-s3-17.s3.ap-northeast-2.amazonaws.com/static/faca0e6c-0cb6-4b2d-b54c-c8a03f1b9a7792213+bytes.jpeg"
                , "순두부찌개", "2022.08.04.", "153", "13", "23", "4",
                "2022.08.04.", "22:00", 300D, 0, user);
        id = recordService.record(record);
        record = Record.createRecord("https://ae-s3-17.s3.ap-northeast-2.amazonaws.com/static/faca0e6c-0cb6-4b2d-b54c-c8a03f1b9a7792213+bytes.jpeg"
                , "우동", "2022.08.05.", "153", "13", "23", "4",
                "2022.08.05.", "22:00", 300D, 0, user);
        id = recordService.record(record);
        record = Record.createRecord("https://ae-s3-17.s3.ap-northeast-2.amazonaws.com/static/faca0e6c-0cb6-4b2d-b54c-c8a03f1b9a7792213+bytes.jpeg"
                , "비빔밥", "2022.08.06.", "153", "13", "23", "4",
                "2022.08.06.", "22:00", 300D, 0, user);
        id = recordService.record(record);
        record = Record.createRecord("https://ae-s3-17.s3.ap-northeast-2.amazonaws.com/static/faca0e6c-0cb6-4b2d-b54c-c8a03f1b9a7792213+bytes.jpeg"
                , "햄버거", "2022.08.07.", "153", "13", "23", "4",
                "2022.08.07.", "22:00", 300D, 0, user);
        id = recordService.record(record);

        // when
        List<DateAnalysisDtoV2> findRecords = analysisService.findRecords(Long.valueOf(user.getId()));

        // then
        assertEquals(findRecords.get(0).getSumCal(), 612.0);
        assertEquals(findRecords.get(1).getSumCal(), 612.0);
        assertEquals(findRecords.get(2).getSumCal(), 612.0);
        assertEquals(findRecords.get(3).getSumCal(), 612.0);
        assertEquals(findRecords.get(4).getSumCal(), 612.0);
        assertEquals(findRecords.get(5).getSumCal(), 612.0);
    }
}
