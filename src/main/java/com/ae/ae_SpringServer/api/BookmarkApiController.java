package com.ae.ae_SpringServer.api;

import com.ae.ae_SpringServer.domain.Bistro;
import com.ae.ae_SpringServer.domain.Bookmark;
import com.ae.ae_SpringServer.domain.User;
import com.ae.ae_SpringServer.service.BistroService;
import com.ae.ae_SpringServer.service.BookmarkService;
import com.ae.ae_SpringServer.service.UserService;
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
public class BookmarkApiController {
    private final BookmarkService bookmarkService;
    private final UserService userService;
    private final BistroService bistroService;

    //7-1
    @PostMapping("api/bookmark")
    public CreateBookmarkResponse createBookmarkResponse(@AuthenticationPrincipal String userId,
                                                         @RequestBody @Valid BookmarkRequest request) {
        User user = userService.findOne(Long.valueOf(userId));
        Bistro bistro = bistroService.findOne(request.id);
        Bookmark bookmark = Bookmark.createBookmark(user, bistro);
        Long id = bookmarkService.create(bookmark);

        return new CreateBookmarkResponse(id.intValue());
    }

    //7-2
    @GetMapping("api/bookmarklist")
    public Result bookmarkList(@AuthenticationPrincipal String userId) {
        List<Bistro> restaurant = bookmarkService.findBookmark(Long.valueOf(userId));
        List<RestaurantDto> restaurantDtos = new ArrayList<>();

        for(Bistro bistro: restaurant) {
            restaurantDtos.add(new RestaurantDto(bistro.getCategory(), bistro.getName(),
                    bistro.getRAddr(), bistro.getLAddr(),
                    bistro.getTel(), bistro.getLa(), bistro.getLo()));
        }
        return new Result(restaurantDtos.size(), restaurantDtos);

    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private Integer count;
        private T data;
    }

    @Data
    private static class BookmarkRequest{
        @NotNull
        private Long id;
    }
    @Data
    @AllArgsConstructor
    private static class CreateBookmarkResponse {
        @NotNull
        private int id;
    }

    @Data
    @AllArgsConstructor
    private static class RestaurantDto {
        private String category;
        @NotNull
        private String name;
        private String roadAddr;
        private String lnmAddr;
        private String telNo;
        @NotNull
        private String la;
        @NotNull
        private String lo;

    }

}
