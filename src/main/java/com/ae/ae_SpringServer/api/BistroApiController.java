package com.ae.ae_SpringServer.api;

import com.ae.ae_SpringServer.domain.Bistro;
import com.ae.ae_SpringServer.service.BistroService;
import com.ae.ae_SpringServer.service.BookmarkService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BistroApiController {
    private final BistroService bistroService;
    private final BookmarkService bookmarkService;

    //6-1
    @PostMapping("/api/bistromiddle")
    public Result middle(@AuthenticationPrincipal String userId, @RequestBody @Valid MiddleRequest request) {
        List<Bistro> bistros = bistroService.getMiddle(request.getWide());
        List<String> middles = new ArrayList<>();

        for(Bistro bistro : bistros) {
            middles.add(bistro.getMiddle());
        }
        return new Result(middles);
    }

    //6-2
    @PostMapping("/api/categories")
    public CategoryListResponse categories(@AuthenticationPrincipal String userId, @RequestBody @Valid CategoryRequest request) {
        List<Bistro> categoryList = bistroService.getCategoryList(request.getWide(), request.getMiddle());
        List<Bistro> categoryGroup = bistroService.getCategories(request.getWide(), request.getMiddle());

        List<CategoryListDto> listDtos = new ArrayList<>();
        List<String> categories = new ArrayList<>();

        for(Bistro bistro : categoryList) {
            listDtos.add(new CategoryListDto(bistro.getCategory(), bistro.getName(), bistro.getRAddr(), bistro.getLAddr(), bistro.getTel()));
        }

        for(Bistro bistro : categoryGroup) {
            categories.add(bistro.getCategory());
        }

        return new CategoryListResponse(categories, listDtos.size(), listDtos);
    }

    //6-3
    @GetMapping("/api/allbistro")
    public Result allBistro(@AuthenticationPrincipal String userId) {
        List<Bistro> allBistro = bistroService.getBistro();
        List<Bistro> bookmark = bookmarkService.findBookmark(Long.valueOf(userId));
        List<BistroDto> bistroDtos = new ArrayList<>();
        for (Bistro bistro : allBistro){
            int isBookmark;
            if(bookmark.indexOf(bistro) != -1) {
                isBookmark = 1;
            } else {
                isBookmark = 0;
            }
            bistroDtos.add(new BistroDto(isBookmark, bistro.getCategory(), bistro.getName(), bistro.getRAddr(), bistro.getLAddr(),
                    bistro.getTel(), bistro.getMenu(), Double.parseDouble(bistro.getLa()), Double.parseDouble(bistro.getLo())));
        }
        return new Result(bistroDtos);
    }




    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    private static class MiddleRequest {
        @NotNull
        private String wide;
    }

    @Data
    private static class CategoryRequest {
        @NotNull
        private String wide;
        @NotNull
        private String middle;
    }

    @Data
    @AllArgsConstructor
    private static class CategoryListDto {
        private String category;
        @NotNull
        private String name;
        private String roadAddr;
        private String lnmAddr;
        private String telNo;
    }

    @Data
    @AllArgsConstructor
    private static class BistroDto {
        private int isBookmark;
        private String category;
        private String name;
        private String roadAddr;
        private String lnmAddr;
        private String telNo;
        private String menuInfo;
        private Double la;
        private Double lo;
    }

    @Data
    @AllArgsConstructor
    private static class CategoryListResponse {
        @NotNull
        private List<String> categories;
        @NotNull
        private int size;
        @NotNull
        private List<CategoryListDto> CategoryList;
    }
}
