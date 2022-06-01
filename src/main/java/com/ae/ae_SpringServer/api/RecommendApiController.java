package com.ae.ae_SpringServer.api;

import com.ae.ae_SpringServer.domain.Food;
import com.ae.ae_SpringServer.domain.Recommend;
import com.ae.ae_SpringServer.domain.User;
import com.ae.ae_SpringServer.service.RecommendService;
import com.ae.ae_SpringServer.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class RecommendApiController {
    private final RecommendService recommendService;
    private final UserService userService;

    @GetMapping("/api/recommend")
    public Result recommend() {
        Long id = Long.valueOf(0);
        User user = userService.findOne(id);
        Food f = recommendService.findRecommend(id);
        Recommend recommend = Recommend.createRecommend(f.getName(), LocalDate.now().toString(), user);
        recommendService.add(recommend);
        return new Result(f);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
