package com.ae.ae_SpringServer.api.v1;

import com.ae.ae_SpringServer.domain.Bistro;
import com.ae.ae_SpringServer.dto.request.CategoryRequestDto;
import com.ae.ae_SpringServer.dto.request.MiddleRequestDto;
import com.ae.ae_SpringServer.dto.response.BistroResponseDto;
import com.ae.ae_SpringServer.dto.response.CategoryListDto;
import com.ae.ae_SpringServer.dto.response.CategoryListResponseDto;
import com.ae.ae_SpringServer.dto.response.ResultResponse;
import com.ae.ae_SpringServer.service.BistroService;
import com.ae.ae_SpringServer.service.BookmarkService;
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
public class BistroApiControllerV1 {
    private final BistroService bistroService;
    private final BookmarkService bookmarkService;

    //6-1
    @PostMapping("/api/bistromiddle")
    public ResultResponse middle(@AuthenticationPrincipal String userId, @RequestBody @Valid MiddleRequestDto request) {
        List<Bistro> bistros = bistroService.getMiddle(request.getWide());
        List<String> middles = new ArrayList<>();

        for(Bistro bistro : bistros) {
            middles.add(bistro.getMiddle());
        }
        return new ResultResponse(middles);
    }

    //6-2
    @PostMapping("/api/categories")
    public CategoryListResponseDto categories(@AuthenticationPrincipal String userId, @RequestBody @Valid CategoryRequestDto request) {
        List<Bistro> categoryList = bistroService.getCategoryList(request.getWide(), request.getMiddle());
        List<Bistro> categoryGroup = bistroService.getCategories(request.getWide(), request.getMiddle());
        List<Bistro> bookmark = bookmarkService.findBookmark(Long.valueOf(userId));

        List<CategoryListDto> listDtos = new ArrayList<>();
        List<String> categories = new ArrayList<>();

        for(Bistro bistro : categoryList) {
            int isBookmark;
            if(bookmark.indexOf(bistro) != -1) {
                isBookmark = 1;
            } else {
                isBookmark = 0;
            }
            listDtos.add(new CategoryListDto(bistro.getId().intValue(), isBookmark, bistro.getCategory(), bistro.getName(), bistro.getRAddr(), bistro.getLAddr(), bistro.getTel()));
        }

        for(Bistro bistro : categoryGroup) {
            categories.add(bistro.getCategory());
        }

        return new CategoryListResponseDto(categories, listDtos.size(), listDtos);
    }

    //6-3
    @GetMapping("/api/allbistro")
    public ResultResponse allBistro(@AuthenticationPrincipal String userId) {
        List<Bistro> allBistro = bistroService.getBistro();
        List<Bistro> bookmark = bookmarkService.findBookmark(Long.valueOf(userId));
        List<BistroResponseDto> bistroDtos = new ArrayList<>();
        for (Bistro bistro : allBistro){
            int isBookmark;
            if(bookmark.indexOf(bistro) != -1) {
                isBookmark = 1;
            } else {
                isBookmark = 0;
            }
            bistroDtos.add(new BistroResponseDto(isBookmark, bistro.getId(), bistro.getCategory(), bistro.getName(), bistro.getRAddr(), bistro.getLAddr(),
                    bistro.getTel(), bistro.getMenu(), Double.parseDouble(bistro.getLa()), Double.parseDouble(bistro.getLo())));
        }
        return new ResultResponse(bistroDtos);
    }


}