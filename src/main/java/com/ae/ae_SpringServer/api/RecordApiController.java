package com.ae.ae_SpringServer.api;

import com.ae.ae_SpringServer.domain.Record;
import com.ae.ae_SpringServer.domain.User;
import com.ae.ae_SpringServer.service.RecordService;
import com.ae.ae_SpringServer.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
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
        Record record = Record.createRecord(request.text, LocalDate.now().toString(), request.calory, request.carb, request.protein, request.fat, user);
        Long id = recordService.record(record);
        return new CreateRecordResponse(id);
    }

    @GetMapping("/api/record")
    public Result records() {
        Long id = Long.valueOf(0);
        List<Record> findRecords = recordService.findRecords(id);
        List<RecordDto> collect = findRecords.stream()
                .map(m -> new RecordDto(m.getText(), m.getServer_date(), m.getCalory(), m.getCarb(), m.getProtein(), m.getFat()))
                .collect(toList());
        return new Result(collect.size(), collect);
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
    }

    @Data
    private static class CreateRecordResponse {
        private Long id;
        public CreateRecordResponse(Long id) { this.id = id; }
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
    }
}
