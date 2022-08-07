package com.ae.ae_SpringServer.service;

import com.ae.ae_SpringServer.domain.Record;
import com.ae.ae_SpringServer.domain.User;
import com.ae.ae_SpringServer.dto.UserUpdateRequestDto;
import com.ae.ae_SpringServer.repository.RecordRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Autowired
    UserService userService;

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


    //1-2
    @Test
    public void 식단날짜별조회() {
        //given : 유저정보, 해당 날짜에 섭취한 식단정보&영양정보 주입
        //유저 주입
        User user = new User();
        user.setName("김김김");
        user.setGender(1);
        user.setAge(50);
        user.setHeight("185");
        user.setWeight("70");
        user.setIcon(2);
        user.setActivity(40);
        em.persist(user);
        //권장 영양소정보 주입
        UserUpdateRequestDto requestDto = new UserUpdateRequestDto();
//        requestDto.setAge(user.getAge());
//        requestDto.setHeight(user.getHeight());
//        requestDto.setWeight(user.getWeight());
//        requestDto.setActivity(user.getActivity()); //애초 user create에 권장이 null 이라 update 도 null 로 들어가는 것
        requestDto.setAge(50);
        requestDto.setHeight("185");
        requestDto.setWeight("70");
        requestDto.setActivity(40);
        userService.update(user.getId(), requestDto);

        //식단 주입
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd."));
        Record record = Record.createRecord("https://ae-s3-17.s3.ap-northeast-2.amazonaws.com/static/faca0e6c-0cb6-4b2d-b54c-c8a03f1b9a7792213+bytes.jpeg"
                , "애호박찌개", date, "350", "100", "50", "30",
                "2022.08.07.", "9:45", 250D, 1, user);
        Long record_id = recordService.record(record);
        Record record2 = Record.createRecord("https://ae-s3-17.s3.ap-northeast-2.amazonaws.com/static/faca0e6c-0cb6-4b2d-b54c-c8a03f1b9a7792213+bytes.jpeg"
                , "2애호박찌개", date, "350", "100", "50", "30",
                "2022.08.07.", "20:45", 250D, 2, user);
        Long record_id2 = recordService.record(record2);
        List<Long> list1 = Arrays.asList(record_id, record_id2);

        //when
        //조회할 날짜 주입
        String searchDate = "2022.08.07.";
        List<Record> findRecords = recordService.findDateRecords(user.getId(), searchDate);

        //then 섭취한 총칼로리, 사용자의 권장칼로리, 식단기록의 id가 맞는지 확인 -> 첫번째,두번째 controller 가 처리
        List<Long> list2 = new ArrayList<>();
        for(Record rs: findRecords){
            list2.add(rs.getId());
        }
        assertEquals(list1, list2);

    }

}
