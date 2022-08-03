package com.ae.ae_SpringServer.api;

import com.ae.ae_SpringServer.domain.Record;
import com.ae.ae_SpringServer.domain.User;
import com.ae.ae_SpringServer.service.RecordService;
import com.ae.ae_SpringServer.service.UserService;
import com.ae.ae_SpringServer.aws.S3Uploader;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.TEXT_PLAIN;

@RestController
@RequiredArgsConstructor
public class RecordApiController {
    private final RecordService recordService;
    private final UserService userService;
    private final S3Uploader s3Uploader;

        //1-1
    @PostMapping(value = "/api/record", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CreateRecordResponse createRecord(@AuthenticationPrincipal String userId,
                                             @RequestParam (value = "image", required = false) MultipartFile multipartFile,
                                             @RequestParam (value = "text", required = true) String text,
                                             @RequestParam (value = "calory", required = false) String calory,
                                             @RequestParam (value = "carb", required = false) String carb,
                                             @RequestParam (value = "protein", required = false) String protein,
                                             @RequestParam (value = "fat", required = false) String fat,
                                             @RequestParam (value = "rdate", required = true) String rdate,
                                             @RequestParam (value = "rtime", required = true) String rtime,
                                             @RequestParam (value = "amount", required = false) Double amount,
                                             @RequestParam (value = "meal", required = true) int meal
                                             ) throws IOException {
        User user = userService.findOne(Long.valueOf(userId));
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd."));
        //S3 Bucket upload
        String img_url = s3Uploader.upload(multipartFile, "static");

        Long id = null;
        Record record = Record.createRecord(img_url, text, date, calory, carb, protein, fat,
                rdate, rtime, amount, meal, user);
        id = recordService.record(record);

        return new CreateRecordResponse(id.intValue());
    }

    //1-2
    @PostMapping("/api/daterecord")
    public DateRecordResponse dateRecords(@AuthenticationPrincipal String userId, @RequestBody @Valid CreateDateRequest request) {
        List<Record> findRecords = recordService.findDateRecords(Long.valueOf(userId), request.date);
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

        User user = userService.findOne(Long.valueOf(userId));
        return new DateRecordResponse(totalCalory.intValue(), totalCarb.intValue(), totalPro.intValue(), totalFat.intValue(),
                (int) Math.round(Double.parseDouble(user.getRcal())), Integer.parseInt(user.getRcarb()), Integer.parseInt(user.getRpro()),
                (int) Math.round(Double.parseDouble(user.getRfat())),
                records);
    }

    //1-3
    @PostMapping("api/detailrecord")
    public Result recordResponse(@AuthenticationPrincipal String userId, @RequestBody @Valid CreateDetailRecordRequest request) {
        List<Record> findDetailRecord = recordService.findDetailOne(Long.valueOf(userId), Long.valueOf(request.record_id));

        List<DetailRecordDto> collect = findDetailRecord.stream()
                .map(m -> new DetailRecordDto(m.getText(), m.getCal(), m.getCarb(), m.getProtein(), m.getFat(), m.getImage_url(), m.getDate(), m.getTime(), m.getAmount()))
                .collect(toList());
        return new Result(collect);

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
    @AllArgsConstructor
    private static class CreateRecordResponse {
        @NotNull
        private int id;
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
