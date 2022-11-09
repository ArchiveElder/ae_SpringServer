package com.ae.ae_SpringServer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class MainCategoryListDto {
    private Long bistroId;

    private int isBookmark;
    @NotNull
    private String name;
    private String roadAddr;
    private String lnmAddr;
    private String telNo;
    private String menu;
    private String la;
    private String lo;
    private String url;
    private String middleCategory;
}
