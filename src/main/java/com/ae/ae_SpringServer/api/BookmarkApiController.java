package com.ae.ae_SpringServer.api;

import com.ae.ae_SpringServer.config.BaseResponse;
import com.ae.ae_SpringServer.domain.Bistro;
import com.ae.ae_SpringServer.domain.BistroV2;
import com.ae.ae_SpringServer.domain.Bookmark;
import com.ae.ae_SpringServer.domain.User;
import com.ae.ae_SpringServer.dto.request.BookmarkRequestDto;
import com.ae.ae_SpringServer.dto.response.CreateBookmarkResponseDto;
import com.ae.ae_SpringServer.dto.response.ResResponse;
import com.ae.ae_SpringServer.dto.response.v2.RestaurantResponseDtoV2;
import com.ae.ae_SpringServer.service.BistroService;
import com.ae.ae_SpringServer.service.BookmarkService;
import com.ae.ae_SpringServer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ae.ae_SpringServer.config.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
public class BookmarkApiController {
    private final BookmarkService bookmarkService;
    private final UserService userService;
    private final BistroService bistroService;

    //[POST] 7-1 북마크 등록
    @PostMapping("api/v2/bookmark")
    public BaseResponse<CreateBookmarkResponseDto> createBookmarkResponse(@AuthenticationPrincipal String userId,
                                                                          @RequestBody @Valid BookmarkRequestDto request) {
        if(userId.equals("INVALID JWT")){
            return new BaseResponse<>(INVALID_JWT);
        }
        if(userId == null) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        User user = userService.findOne(Long.valueOf(userId));
        if (request.getBistroId() == null || request.getBistroId().equals("")){
            return new BaseResponse<>(POST_BOOKMARK_NO_BISTRO_ID);
        }
        // 북마크 상태 조회
        List<BistroV2> restaurant = bookmarkService.findBookmarkV2(Long.valueOf(userId));
        List<Long> restaurantId = restaurant.stream().map(BistroV2::getId).collect(Collectors.toList());
        Long count = restaurantId.stream().filter(m-> request.getBistroId().equals(m)).count();
        if(count > 0 ){
            Long i = bookmarkService.findBookmarkId(Long.valueOf(userId), request.getBistroId());
            return new BaseResponse<>(POST_BOOKMARK_PRESENT_BISTRO);
        }
        // 북마크 식당의 존재여부 확인
        BistroV2 bistro = bistroService.findOneV2(request.getBistroId());
        if(bistro == null) return new BaseResponse<>(POST_BOOKMARK_WRONG_BISTRO);
        Bistro bistro_v1 = bistroService.findV1One(request.getBistroId());

        Bookmark bookmark = Bookmark.createBookmark(user, bistro_v1, request.getBistroId());
        Long id = bookmarkService.create(bookmark);

        return new BaseResponse<>(new CreateBookmarkResponseDto(id.intValue()));
    }

    //[GET] 7-2 즐겨찾기 조회
    @GetMapping("api/v2/bookmarklist")
    public BaseResponse<ResResponse> bookmarkList(@AuthenticationPrincipal String userId) {
        if(userId.equals("INVALID JWT")){
            return new BaseResponse<>(INVALID_JWT);
        }
        if(userId == null) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        List<BistroV2> restaurant = bookmarkService.findBookmarkV2(Long.valueOf(userId));
        List<RestaurantResponseDtoV2> restaurantDtos = new ArrayList<>();

        for(BistroV2 bistro: restaurant) {
            restaurantDtos.add(new RestaurantResponseDtoV2(bistro.getId().intValue(), bistro.getCategory(), bistro.getName(),
                    bistro.getRAddr(), bistro.getLAddr(),
                    bistro.getTel(), bistro.getLa(), bistro.getLo(), bistro.getBistroUrl()));
        }
        if(restaurant.size() > 0){
            return new BaseResponse<>(new ResResponse(restaurantDtos.size(), restaurantDtos));

        }else return new BaseResponse<>(POST_BOOKMARK_LIST_EMPTY);

    }

    //[DELETE] 7-3 즐겨찾기 삭제
    @DeleteMapping("api/v2/del/bookmark")
    public BaseResponse<CreateBookmarkResponseDto> deleteBookmark(@AuthenticationPrincipal String userId,
                                                                  @RequestBody @Valid BookmarkRequestDto request){
        if(userId.equals("INVALID JWT")){
            return new BaseResponse<>(INVALID_JWT);
        }
        if(userId == null) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        List<BistroV2> restaurant = bookmarkService.findBookmarkV2(Long.valueOf(userId));
        Long count =restaurant.stream().map(BistroV2::getId).collect(Collectors.toList())
                .stream().filter(
                        m-> request.getBistroId().equals(m))
                .count();
        if(count > 0 ){
            Long bistroId = bookmarkService.deleteBookmark(Long.valueOf(userId), request.getBistroId());
            return new BaseResponse<>(new CreateBookmarkResponseDto(bistroId.intValue()));

        } else return new BaseResponse<>(POST_BOOMARK_THERE_NO_BISTRO);

    }


}
