package com.ae.ae_SpringServer.api.v1;

import com.ae.ae_SpringServer.domain.Bistro;
import com.ae.ae_SpringServer.domain.Bookmark;
import com.ae.ae_SpringServer.domain.User;
import com.ae.ae_SpringServer.dto.request.BookmarkRequestDto;
import com.ae.ae_SpringServer.dto.response.CreateBookmarkResponseDto;
import com.ae.ae_SpringServer.dto.response.ResResponse;
import com.ae.ae_SpringServer.dto.response.RestaurantResponseDto;
import com.ae.ae_SpringServer.service.BistroService;
import com.ae.ae_SpringServer.service.BookmarkService;
import com.ae.ae_SpringServer.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookmarkApiControllerV1 {
    private final BookmarkService bookmarkService;
    private final UserService userService;
    private final BistroService bistroService;

    //7-1
    @PostMapping("api/bookmark")
    public CreateBookmarkResponseDto createBookmarkResponse(@AuthenticationPrincipal String userId,
                                                            @RequestBody @Valid BookmarkRequestDto request) {
        User user = userService.findOne(Long.valueOf(userId));
        List<Bistro> restaurant = bookmarkService.findBookmark(Long.valueOf(userId));
        List<Long> restaurantId = restaurant.stream().map(Bistro::getId).collect(Collectors.toList());
        Long count = restaurantId.stream().filter(m-> request.getBistroId().equals(m)).count();
        if(count > 0 ){
            Long i = bookmarkService.findBookmarkId(Long.valueOf(userId), request.getBistroId());
            return new CreateBookmarkResponseDto(i.intValue());
        }
        Bistro bistro = bistroService.findV1One(request.getBistroId());
        Bookmark bookmark = Bookmark.createBookmark(user, bistro, null);
        Long id = bookmarkService.create(bookmark);

        return new CreateBookmarkResponseDto(id.intValue());
    }

    //7-2
    @GetMapping("api/bookmarklist")
    public ResResponse bookmarkList(@AuthenticationPrincipal String userId) {
        List<Bistro> restaurant = bookmarkService.findBookmark(Long.valueOf(userId));
        List<RestaurantResponseDto> restaurantDtos = new ArrayList<>();

        for(Bistro bistro: restaurant) {
            restaurantDtos.add(new RestaurantResponseDto(bistro.getId().intValue(), bistro.getCategory(), bistro.getName(),
                    bistro.getRAddr(), bistro.getLAddr(),
                    bistro.getTel(), bistro.getLa(), bistro.getLo()));
        }
        return new ResResponse(restaurantDtos.size(), restaurantDtos);

    }

    //7-3
    @DeleteMapping("api/del/bookmark")
    public CreateBookmarkResponseDto deleteBookmark(@AuthenticationPrincipal String userId,
                                                    @RequestBody @Valid BookmarkRequestDto request){
        Long bistroId = bookmarkService.deleteBookmark(Long.valueOf(userId), request.getBistroId());
        return new CreateBookmarkResponseDto(bistroId.intValue());

    }



}