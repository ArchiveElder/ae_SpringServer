package com.ae.ae_SpringServer.api;

import com.ae.ae_SpringServer.domain.Bistro;
import com.ae.ae_SpringServer.service.BistroService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BistroApiController {
    private final BistroService bistroService;

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
        List<BistroDto> bistroDtos = new ArrayList<>();
        for (Bistro bistro : allBistro){
            bistroDtos.add(new BistroDto(bistro.getCategory(), bistro.getName(), bistro.getRAddr(), bistro.getLAddr(),
                    bistro.getTel(), bistro.getMenu(), bistro.getLa(), bistro.getLo()));
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
        private String wide;
    }

    @Data
    private static class CategoryRequest {
        private String wide;
        private String middle;
    }

    @Data
    @AllArgsConstructor
    private static class CategoryListDto {
        private String category;
        private String name;
        private String roadAddr;
        private String lnmAddr;
        private String telNo;
    }

    @Data
    @AllArgsConstructor
    private static class BistroDto {
        private String category;
        private String name;
        private String roadAddr;
        private String lnmAddr;
        private String telNo;
        private String menuInfo;
        private String la;
        private String lo;
    }

    @Data
    @AllArgsConstructor
    private static class CategoryListResponse {
        private List<String> categories;
        private int size;
        private List<CategoryListDto> CategoryList;
    }
}
