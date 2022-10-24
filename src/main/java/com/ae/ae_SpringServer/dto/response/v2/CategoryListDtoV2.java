package com.ae.ae_SpringServer.dto.response.v2;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
@AllArgsConstructor
public class CategoryListDtoV2 {
    private int bistroId;
    private int isBookmark;
    private String category;
    @NotNull
    private String name;
    private String roadAddr;
    private String lnmAddr;
    private String telNo;
    private String bistroUrl;
}
