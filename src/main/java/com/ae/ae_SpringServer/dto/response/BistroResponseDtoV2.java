package com.ae.ae_SpringServer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BistroResponseDtoV2 {
    private int isBookmark;
    private Long bistro_id;
    private String category;
    private String name;
    private String roadAddr;
    private String lnmAddr;
    private String telNo;
    private String menuInfo;
    private Double la;
    private Double lo;
    private String bistroUrl;
}
