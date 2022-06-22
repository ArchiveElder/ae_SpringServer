package com.ae.ae_SpringServer.api;

import com.ae.ae_SpringServer.domain.Record;
import com.ae.ae_SpringServer.domain.User;
import com.ae.ae_SpringServer.service.RecordService;
import com.ae.ae_SpringServer.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class RecordApiController {
    private final RecordService recordService;
    private final UserService userService;

    //1-1
    @PostMapping("/api/record")
    public CreateRecordResponse createRecord(@RequestBody @Valid CreateRecordRequest request) {
        User user = userService.findOne(Long.valueOf(0));
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd."));
        Long id = null;
        for(CreateDto dto: request.creates) {
            Record record = Record.createRecord(dto.text, date, dto.calory, dto.carb, dto.protein, dto.fat, request.rdate, request.rtime,
                    dto.amount, request.meal, user);
            id = recordService.record(record);
        }
        return new CreateRecordResponse(id.intValue());
    }

    //1-2
    @PostMapping("/api/daterecord")
    public DateRecordResponse dateRecords(@RequestBody @Valid CreateDateRequest request) {
        Long id = Long.valueOf(0);
        List<Record> findRecords = recordService.findDateRecords(id, request.date);
        List<DateRecordDto> bRecords = new ArrayList<DateRecordDto>();
        List<DateRecordDto> lRecords = new ArrayList<DateRecordDto>();
        List<DateRecordDto> dRecords = new ArrayList<DateRecordDto>();
        Double bCal = Double.valueOf(0);
        Double lCal = Double.valueOf(0);
        Double dCal = Double.valueOf(0);
        Double totalCalory = Double.valueOf(0);
        Double totalCarb = Double.valueOf(0);
        Double totalPro = Double.valueOf(0);
        Double totalFat = Double.valueOf(0);
        for(Record record: findRecords) {
            if(record.getMeal() == 0) {
                bCal += Double.parseDouble(record.getCal());
                bRecords.add(new DateRecordDto(record.getText(), record.getServer_date(), record.getCal(), record.getCarb(), record.getProtein(),
                        record.getFat(), record.getDate(), record.getTime(), record.getAmount()));
            } else if(record.getMeal() == 1) {
                lCal += Double.parseDouble(record.getCal());
                lRecords.add(new DateRecordDto(record.getText(), record.getServer_date(), record.getCal(), record.getCarb(), record.getProtein(),
                        record.getFat(), record.getDate(), record.getTime(), record.getAmount()));
            } else if(record.getMeal() == 2) {
                dCal += Double.parseDouble(record.getCal());
                dRecords.add(new DateRecordDto(record.getText(), record.getServer_date(), record.getCal(), record.getCarb(), record.getProtein(),
                        record.getFat(), record.getDate(), record.getTime(), record.getAmount()));
            }
            totalCalory += Double.parseDouble(record.getCal());
            totalCarb += Double.parseDouble(record.getCarb());
            totalPro += Double.parseDouble(record.getProtein());
            totalFat += Double.parseDouble(record.getFat());
        }
        RecordsDto b = new RecordsDto(0, bCal.intValue(), bRecords);
        RecordsDto l = new RecordsDto(1, lCal.intValue(), lRecords);
        RecordsDto d = new RecordsDto(2, dCal.intValue(), dRecords);
        List<RecordsDto> records = new ArrayList<RecordsDto>();
        records.add(b); records.add(l); records.add(d);

        User user = userService.findOne(id);

        return new DateRecordResponse(totalCalory.intValue(), totalCarb.intValue(), totalPro.intValue(), totalFat.intValue(),
                user.getUcalory(), user.getUcarb(), user.getUpro(), user.getUfat(),
                records);
    }

    // 해야할것: 플라스크 서버에 전달해줄 식단 조회 (최신 6개) - 서버와 api 통신할 때 하기

    // 해야할것: 식단 각각 상세 조회

    @Data
    private static class CreateRecordRequest {
        @NotEmpty
        private List<CreateDto> creates;

        @NotNull
        private String rdate;

        @NotNull
        private String rtime;

        @NotNull
        private int meal;
    }

    @Data
    private static class CreateDateRequest {
        @NotNull
        private String date;
    }

    @Data
    private static class CreateRecordResponse {
        private int id;
        public CreateRecordResponse(int id) { this.id = id; }
    }

    @Data
    @AllArgsConstructor
    private static class DateRecordResponse {
        private int totalCalory;
        private int totalCarb;
        private int totalPro;
        private int totalFat;
        private int recommCalory;
        private int recommCarb;
        private int recommPro;
        private int recommFat;
        private List<RecordsDto> records;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private Integer count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class CreateDto {
        private String text;
        private String calory;
        private String carb;
        private String protein;
        private String fat;
        private Double amount;
    }

    @Data
    @AllArgsConstructor
    static class RecordsDto {
        private int meal; // 아침, 점심, 저녁 구분
        private int mCal; // 한끼니의 총 칼로리
        private List<DateRecordDto> record; // 한끼니의 총 식단단
   }

   @Data
   @AllArgsConstructor
   static class DateRecordDto {
       private String text;
       private String date;
       private String calory;
       private String carb;
       private String protein;
       private String fat;
       private String rdate;
       private String rtime;
       private Double amount;
    }

    @Data
    @AllArgsConstructor
    static class RecordDto {
        private String text;
        private String date;
        private String calory;
        private String carb;
        private String protein;
        private String fat;
        private String rdate;
        private String rtime;
        private Double amount;
        private int meal;
    }
}
