package com.ae.ae_SpringServer.dto.response;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RecordsDto {
    private int meal; // 아침, 점심, 저녁 구분
    private int mCal; // 한끼니의 총 칼로리
    private List<DateRecordDto> record; // 한끼니의 총 식단
}
