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
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class RecordApiController {
    private final RecordService recordService;
    private final UserService userService;

    @PostMapping("/api/record")
    public CreateRecordResponse createRecord(@RequestBody @Valid CreateRecordRequest request) {
        User user = userService.findOne(Long.valueOf(0));
        Record record = Record.createRecord(request.text, LocalDate.now().toString(), request.calory, request.carb, request.protein, request.fat,
                request.rdate, request.rtime, request.amount, request.meal, user);
        Long id = recordService.record(record);
        return new CreateRecordResponse(id.intValue());
    }

    @GetMapping("/api/record")
    public Result records() {
        Long id = Long.valueOf(0);
        List<Record> findRecords = recordService.findRecords(id);
        List<RecordDto> collect = findRecords.stream()
                .map(m -> new RecordDto(m.getText(), m.getServer_date(), m.getCal(), m.getCarb(), m.getProtein(), m.getFat(),
                        m.getDate(), m.getTime(), m.getAmount(), m.getMeal()))
                .collect(toList());
        return new Result(collect.size(), collect);
    }

    @PostMapping("/api/daterecord")
    public DateRecordResponse dateRecords(@RequestBody @Valid CreateDateRequest request) {
        Long id = Long.valueOf(0);
        List<Record> findRecords = recordService.findDateRecords(id, request.date);
        List<RecordDto> collect = findRecords.stream()
                .map(m -> new RecordDto(m.getText(), m.getServer_date(), m.getCal(), m.getCarb(), m.getProtein(), m.getFat(),
                        m.getDate(), m.getTime(), m.getAmount(), m.getMeal()))
                .collect(toList());
        Double totalCalory = Double.valueOf(0);
        Double totalCarb = Double.valueOf(0);
        Double totalPro = Double.valueOf(0);
        Double totalFat = Double.valueOf(0);
        Double bCal = Double.valueOf(0);
        Double lCal = Double.valueOf(0);
        Double dCal = Double.valueOf(0);
        List<Integer> mealCalory = Collections.emptyList();
        User user = userService.findOne(id);
        for(Record record: findRecords) {
            if(record.getMeal() == 0) {
                bCal += Double.parseDouble(record.getCal());
            } else if(record.getMeal() == 1) {
                lCal += Double.parseDouble(record.getCal());
            } else if(record.getMeal() == 2) {
                dCal += Double.parseDouble(record.getCal());
            }
            totalCalory += Double.parseDouble(record.getCal());
            totalCarb += Double.parseDouble(record.getCarb());
            totalPro += Double.parseDouble(record.getProtein());
            totalFat += Double.parseDouble(record.getFat());
        }
        mealCalory.add(bCal.intValue());
        mealCalory.add(lCal.intValue());
        mealCalory.add(dCal.intValue());
        return new DateRecordResponse(totalCalory.intValue(), totalCarb.intValue(), totalPro.intValue(), totalFat.intValue(),
                user.getUcalory(), user.getUcarb(), user.getUpro(), user.getUfat(),
                mealCalory,
                collect);
    }

    // 해야할것: 플라스크 서버에 전달해줄 식단 조회 (최신 6개) - 서버와 api 통신할 때 하기

    // 해야할것: 식단 각각 상세 조회

    @Data
    private static class CreateRecordRequest {
        @NotEmpty
        private String text;

        @NotNull
        private String calory;

        @NotNull
        private String carb;

        @NotNull
        private String protein;

        @NotNull
        private String fat;

        @NotNull
        private String rdate;

        @NotNull
        private String rtime;

        @NotNull
        private Double amount;

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
        private List<Integer> mealCalory;
        private List<RecordDto> records;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private Integer count;
        private T data;
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
