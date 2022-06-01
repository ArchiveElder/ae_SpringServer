package com.ae.ae_SpringServer.api;

import com.ae.ae_SpringServer.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;

    @GetMapping("/api/userbadge")
    public Result userBadge() {
        Long id = Long.valueOf(0);
        int num = userService.findBadge(id);
        return new Result(num);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
