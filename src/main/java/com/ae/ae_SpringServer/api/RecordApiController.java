package com.ae.ae_SpringServer.api;

import com.ae.ae_SpringServer.domain.Record;
import com.ae.ae_SpringServer.domain.User;
import com.ae.ae_SpringServer.service.RecordService;
import com.ae.ae_SpringServer.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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
        User user = userService.findOne(Long.valueOf(3));
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd."));
        Long id = null;
        Record record = Record.createRecord(request.text, date, request.calory, request.carb, request.protein, request.fat,
                request.rdate, request.rtime, request.amount, request.meal, user);
        id = recordService.record(record);

        return new CreateRecordResponse(id.intValue());
    }

    //1-2
    @PostMapping("/api/daterecord")
    public DateRecordResponse dateRecords(@RequestBody @Valid CreateDateRequest request) {
        Long id = Long.valueOf(3);
        List<Record> findRecords = recordService.findDateRecords(id, request.date);
        List<DateRecordDto> bRecords = new ArrayList<DateRecordDto>();
        List<DateRecordDto> lRecords = new ArrayList<DateRecordDto>();
        List<DateRecordDto> dRecords = new ArrayList<DateRecordDto>();
        Double bCal, lCal, dCal, totalCalory, totalCarb, totalPro, totalFat;
        bCal = lCal =  dCal = totalCalory = totalCarb = totalPro = totalFat = 0D;

        for(Record record: findRecords) {
            if(record.getMeal() == 0) {
                bCal += Double.parseDouble(record.getCal());
                bRecords.add(new DateRecordDto(record.getId().intValue(), record.getText(), record.getServer_date(), record.getCal(), record.getCarb(), record.getProtein(),
                        record.getFat(), record.getDate(), record.getTime(), record.getAmount()));
            } else if(record.getMeal() == 1) {
                lCal += Double.parseDouble(record.getCal());
                lRecords.add(new DateRecordDto(record.getId().intValue(), record.getText(), record.getServer_date(), record.getCal(), record.getCarb(), record.getProtein(),
                        record.getFat(), record.getDate(), record.getTime(), record.getAmount()));
            } else if(record.getMeal() == 2) {
                dCal += Double.parseDouble(record.getCal());
                dRecords.add(new DateRecordDto(record.getId().intValue(), record.getText(), record.getServer_date(), record.getCal(), record.getCarb(), record.getProtein(),
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

    //1-3
    @PostMapping("api/detailrecord")
    public Result recordResponse(@RequestBody @Valid CreateDetailRecordRequest request) {
        Long id = Long.valueOf(3);
        List<Record> findDetailRecord = recordService.findDetailOne(id, Long.valueOf(request.record_id));

        List<DetailRecordDto> collect = findDetailRecord.stream()
                .map(m -> new DetailRecordDto(m.getText(), m.getCal(), m.getCarb(), m.getProtein(), m.getFat(), m.getImage_url(), m.getDate(), m.getTime(), m.getAmount()))
                .collect(toList());
        return new Result(collect);

    }
    // 해야할것: 플라스크 서버에 전달해줄 식단 조회 (최신 6개) - 서버와 api 통신할 때 하기


    @Data
    private static class CreateRecordRequest {
        @NotNull
        private String text;
        private String calory;
        private String carb;
        private String protein;
        private String fat;
        private Double amount;
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
    private static class CreateDetailRecordRequest {
        @NotNull
        private int record_id;

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
        //private Integer count;
        private T data;
    }


    @Data
    @AllArgsConstructor
    static class RecordsDto {
        private int meal; // 아침, 점심, 저녁 구분
        private int mCal; // 한끼니의 총 칼로리
        private List<DateRecordDto> record; // 한끼니의 총 식단
   }

   @Data
   @AllArgsConstructor
   static class DateRecordDto {
       private int record_id;
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
    static class DetailRecordDto {
        private String text;
        private String cal;
        private String carb;
        private String protein;
        private String fat;
        private String image_url;
        private String date;
        private String time;
        private Double amount;
    }


}
