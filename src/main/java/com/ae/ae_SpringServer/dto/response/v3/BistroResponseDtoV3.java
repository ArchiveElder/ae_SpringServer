package com.ae.ae_SpringServer.dto.response.v3;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BistroResponseDtoV3 {
    private int isBookmark;
    private Long bistro_id;
    private String name;
    private String roadAddr;
    private String lnmAddr;
    private String telNo;
    private String menuInfo;
    private Double la;
    private Double lo;
    private String bistroUrl;
    private String mainCategory;
    private String middleCategory;
    private String siteWide;
    private String siteMiddle;
}
