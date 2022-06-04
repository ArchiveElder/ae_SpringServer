package com.ae.ae_SpringServer.api;

import com.ae.ae_SpringServer.domain.Food;
import com.ae.ae_SpringServer.service.FoodService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class FoodApiController {
    private final FoodService foodService;

    @GetMapping("/api/foodname")
    public Result foods() {
        List<Food> findFoods = foodService.findAllFoods();
        List<FoodTypeDto> collect = findFoods.stream()
                .map(m -> new FoodTypeDto(m.getId(), m.getName()))
                .collect(toList());
        return new Result(collect.size(), collect);


    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private Integer count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class FoodTypeDto {
        private Long id;
        private String name;
    }
}
