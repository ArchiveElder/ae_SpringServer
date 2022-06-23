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

    //1-3
    @PostMapping("api/detailrecord")
    public DetailRecordResponse recordResponse(@RequestBody @Valid CreateDetailRecordRequest request) {
        Long id = Long.valueOf(2);
        Double totalCalory, totalCarb, totalPro, totalFat;
        totalCalory = totalCarb = totalPro =  totalFat = 0D;
        // user_id= 2인 사용자의, 요청 날짜와 끼니를 가지고 상세 식단을 조회하는 것이 목적
        // 쿼리를 통해 해당 날짜의 해당 끼니 먹은 음식들의 음식명, 칼로리, 탄, 단, 지, 이미지url 받아옴
        List<Record> findDetailRecord = recordService.findDetailOne(id, request.date, request.meal);

        List<DetailRecordDto> detailDtos= new ArrayList<DetailRecordDto>();
        for(Record record : findDetailRecord) {
            detailDtos.add(new DetailRecordDto(record.getText(), record.getCal(), record.getCarb(), record.getProtein(), record.getFat(), record.getImage_url()));

            totalCalory += Double.parseDouble(record.getCal());
            totalCarb += Double.parseDouble(record.getCarb());
            totalPro += Double.parseDouble(record.getProtein());
            totalFat += Double.parseDouble(record.getFat());

        }

        return new DetailRecordResponse(totalCalory.intValue(), totalCarb.intValue() ,totalPro.intValue(), totalFat.intValue(),
                detailDtos);

    }

    //1-4
    @PostMapping("api/mealalbum")
    public MealAlbumResponse mealAlbumResponse(@RequestBody @Valid CreateMealAlbumRequest request) {
        Long id = Long.valueOf(2); // 사용자 아이디
        User user = userService.findOne(id);
        List<Record> findMealAlbum = recordService.findRecordsMeal(user.getId());
        // 날짜, 끼니[칼로리 합, 첫식사시간, 첫이미지url  ]
        Double totalCalory = 0D;
        MealDto mealDtos = null;
        List<MealAlbumDto> collect = findMealAlbum.stream()
                .map(m -> new MealAlbumDto(m.getTime(), m.getImage_url()))
                .collect(toList());

        for(Record record : findMealAlbum) {
            totalCalory += Double.parseDouble(record.getCal());
            mealDtos = new MealDto(record.getDate(), record.getMeal());
            new MealDto(record.getDate(), record.getMeal());

        }
        return new MealAlbumResponse(mealDtos, totalCalory.intValue(), collect);


    }


    // 해야할것: 플라스크 서버에 전달해줄 식단 조회 (최신 6개) - 서버와 api 통신할 때 하기


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
    private static class CreateDetailRecordRequest {
        @NotNull
        private String date;
        @NotNull
        private int meal;

    }

    @Data
    private static class CreateMealAlbumRequest {
        private int id;
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
    private static class DetailRecordResponse {
        private int totalCal;   //끼니별 총 칼로리
        private int totalCarb;  //끼니별 영양소 별 총합
        private int totalPro;
        private int totalFat;
        private List<DetailRecordDto> detailDtos;
    }
    @Data @AllArgsConstructor
    private static class MealAlbumResponse {
        // 날짜, 끼니[칼로리 합, 첫식사시간, 첫이미지url  ]
        MealDto mealDto;
        private int totalCal;
        private List<MealAlbumDto> mealAlbumDtos;

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
        private List<DateRecordDto> record; // 한끼니의 총 식단
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
    static class DetailRecordDto {
        private String text;
        private String cal;
        private String carb;
        private String protein;
        private String fat;
        private String image_url;
    }

    @Data @AllArgsConstructor
    static class MealAlbumDto{
        // 날짜, 끼니[칼로리 합, 첫식사시간, 첫이미지url  ]
        //private String mealTotalCal;
        private String mealFrstTime;
        private String mealFrstimg;

    }

    @Data @AllArgsConstructor @Setter
    static class MealDto {
        private String date;
        private int meal;
    }
}
