package com.ae.ae_SpringServer.api;

import com.ae.ae_SpringServer.domain.Food;
import com.ae.ae_SpringServer.service.FoodService;
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
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class FoodApiController {
    private final FoodService foodService;

    //2-1
    @GetMapping("/api/foodname")
    public Result foods(@AuthenticationPrincipal String userId) {
        List<Food> findFoods = foodService.findAllFoods();
        List<FoodTypeDto> collect = findFoods.stream()
                .map(m -> new FoodTypeDto(m.getId(), m.getName()))
                .collect(toList());
        return new Result(collect.size(), collect);


    }
    //2-2
    @PostMapping("/api/food")
    public Result foodResponse(@AuthenticationPrincipal String userId, @RequestBody @Valid CreateFoodRequest request){
        List<Food> findFood = foodService.findFood(request.id);
        List<FoodDto> collect = findFood.stream()
                .map(m -> new FoodDto(m.getName(), m.getCapacity(), m.getCalory(), m.getCarb(), m.getPro(), m.getFat()))
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
        @NotNull
        private Long id;
        @NotNull
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class FoodDto {
        @NotNull
        private String name;
        private int capacity;
        private double calory;
        private double carb;
        private double pro;
        private double fat;
    }

    @Data
    private static class CreateFoodRequest {
        @NotNull
        private Long id;
    }

}
