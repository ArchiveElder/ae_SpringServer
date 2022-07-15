package com.ae.ae_SpringServer.api;

import com.ae.ae_SpringServer.domain.Bistro;
import com.ae.ae_SpringServer.domain.Bookmark;
import com.ae.ae_SpringServer.domain.User;
import com.ae.ae_SpringServer.service.BistroService;
import com.ae.ae_SpringServer.service.BookmarkService;
import com.ae.ae_SpringServer.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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

    @Data
    private static class BookmarkRequest{
        @NotNull
        private Long id;
    }
    @Data
    private static class CreateBookmarkResponse {
        private int id;
        public CreateBookmarkResponse(int id) { this.id = id; }
    }
}
