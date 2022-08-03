package com.ae.ae_SpringServer.api;

import com.ae.ae_SpringServer.domain.Ingredient;
import com.ae.ae_SpringServer.service.IngredientService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;
import static java.util.stream.Collectors.toList;


@RestController
@RequiredArgsConstructor
public class IngredientApiController {
    private final IngredientService ingredientService;

    //9-1
    @GetMapping("/api/ingredient")
    public Result ingredients(@AuthenticationPrincipal String userId) {
        List<Ingredient> findIngredients = ingredientService.findAllIngredients();
        List<IngredientDto> collect = findIngredients.stream()
                .map(m -> new IngredientDto(m.getId(), m.getName()))
                .collect(toList());
        return new Result(collect.size(), collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        @NotNull
        private Integer count;
        private T data;
    }
    @Data
    @AllArgsConstructor
    static class IngredientDto {
        @NotNull
        private Long id;
        @NotNull
        private String name;
    }
}
