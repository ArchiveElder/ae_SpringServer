package com.ae.ae_SpringServer.api;

import com.ae.ae_SpringServer.config.BaseResponse;
import com.ae.ae_SpringServer.domain.BistroV2;
import com.ae.ae_SpringServer.domain.User;
import com.ae.ae_SpringServer.dto.request.CategoryRequestDto;
import com.ae.ae_SpringServer.dto.request.MiddleRequestDto;
import com.ae.ae_SpringServer.dto.response.*;
import com.ae.ae_SpringServer.dto.response.v2.BistroResponseDtoV2;
import com.ae.ae_SpringServer.dto.response.v2.CategoryListDtoV2;
import com.ae.ae_SpringServer.dto.response.v2.CategoryListResponseDtoV2;
import com.ae.ae_SpringServer.service.BistroService;
import com.ae.ae_SpringServer.service.BookmarkService;
import com.ae.ae_SpringServer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static com.ae.ae_SpringServer.config.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
public class BistroApiController {
    private final BistroService bistroService;
    private final BookmarkService bookmarkService;
    private final UserService userService;

    //[POST] 6-1 음식점 중분류 조회
    @PostMapping("/api/v2/bistromiddle")
    public BaseResponse<ResultResponse> middle(@AuthenticationPrincipal String userId, @RequestBody @Valid MiddleRequestDto request) {
        if(userId.equals("INVALID JWT")){
            return new BaseResponse<>(INVALID_JWT);
        }
        if(userId == null) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        User user = userService.findOne(Long.valueOf(userId));
        if (user == null) {
            return new BaseResponse<>(INVALID_JWT);
        }

        if(request.getWide().isEmpty() || request.getWide().equals("")) {
            return new BaseResponse<>(POST_BISTRO_NO_WIDE);
        }
        List<BistroV2> bistros = bistroService.getMiddleV2(request.getWide());
        List<String> middles = new ArrayList<>();

        for(BistroV2 bistro : bistros) {
            middles.add(bistro.getMiddle());
        }
        return new BaseResponse<>(new ResultResponse(middles));
    }

    //[POST] 6-2 대분류,중분류별 음식점조회
    @PostMapping("/api/v2/categories")
    public BaseResponse<CategoryListResponseDtoV2> categories(@AuthenticationPrincipal String userId, @RequestBody @Valid CategoryRequestDto request) {
        if(userId.equals("INVALID JWT")){
            return new BaseResponse<>(INVALID_JWT);
        }
        if(userId == null) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        User user = userService.findOne(Long.valueOf(userId));
        if (user == null) {
            return new BaseResponse<>(INVALID_JWT);
        }

        if(request.getWide().isEmpty() || request.getWide().equals("")) {
            return new BaseResponse<>(POST_BISTRO_NO_WIDE);
        }

        if(request.getMiddle().isEmpty() || request.getMiddle().equals("")) {
            return new BaseResponse<>(POST_BISTRO_NO_MIDDLE);
        }
        List<BistroV2> categoryList = bistroService.getCategoryListV2(request.getWide(), request.getMiddle());
        List<BistroV2> categoryGroup = bistroService.getCategoriesV2(request.getWide(), request.getMiddle());
        List<BistroV2> bookmark = bookmarkService.findBookmarkV2(Long.valueOf(userId));

        List<CategoryListDtoV2> listDtos = new ArrayList<>();
        List<String> categories = new ArrayList<>();

        for(BistroV2 bistro : categoryList) {
            int isBookmark;
            if(bookmark.indexOf(bistro) != -1) {
                isBookmark = 1;
            } else {
                isBookmark = 0;
            }
            listDtos.add(new CategoryListDtoV2(bistro.getId().intValue(), isBookmark, bistro.getCategory(), bistro.getName(),
                    bistro.getRAddr(), bistro.getLAddr(), bistro.getTel(), bistro.getBistroUrl()));
        }

        for(BistroV2 bistro : categoryGroup) {
            categories.add(bistro.getCategory());
        }

        return new BaseResponse<>(new CategoryListResponseDtoV2(categories, listDtos.size(), listDtos));
    }

    //[POST] 6-3 (지도)음식점 전체 조회
    @GetMapping("/api/v2/allbistro")
    public BaseResponse<ResultResponse> allBistro(@AuthenticationPrincipal String userId) {
        if(userId.equals("INVALID JWT")){
            return new BaseResponse<>(INVALID_JWT);
        }
        if(userId == null) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        User user = userService.findOne(Long.valueOf(userId));
        if (user == null) {
            return new BaseResponse<>(INVALID_JWT);
        }
        List<BistroV2> allBistro = bistroService.getBistroV2();
        List<BistroV2> bookmark = bookmarkService.findBookmarkV2(Long.valueOf(userId));
        List<BistroResponseDtoV2> bistroDtos = new ArrayList<>();
        for (BistroV2 bistro : allBistro){
            int isBookmark;
            if(bookmark.indexOf(bistro) != -1) {
                isBookmark = 1;
            } else {
                isBookmark = 0;
            }
            bistroDtos.add(new BistroResponseDtoV2(isBookmark, bistro.getId(), bistro.getCategory(), bistro.getName(), bistro.getRAddr(), bistro.getLAddr(),
                    bistro.getTel(), bistro.getMenu(), Double.parseDouble(bistro.getLa()), Double.parseDouble(bistro.getLo()), bistro.getBistroUrl()));
        }
        return new BaseResponse<>(new ResultResponse(bistroDtos));
    }
}
