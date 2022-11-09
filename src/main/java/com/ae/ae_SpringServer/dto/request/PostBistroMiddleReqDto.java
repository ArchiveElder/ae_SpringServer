package com.ae.ae_SpringServer.dto.request;

import lombok.Data;

@Data
public class PostBistroMiddleReqDto {
    private String siteWide;
    private String siteMiddle;
    private String mainCategory;
    private String middleCategory;
}
