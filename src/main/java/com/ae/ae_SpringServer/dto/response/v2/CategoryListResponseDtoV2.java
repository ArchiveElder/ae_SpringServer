package com.ae.ae_SpringServer.dto.response.v2;

import com.ae.ae_SpringServer.dto.response.v2.CategoryListDtoV2;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
public class CategoryListResponseDtoV2 {
    @NotNull
    private List<String> categories;
    @NotNull
    private int size;
    @NotNull
    private List<CategoryListDtoV2> CategoryList;
}
