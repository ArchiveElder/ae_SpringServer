package com.ae.ae_SpringServer.api;

import com.ae.ae_SpringServer.config.BaseResponse;
import com.ae.ae_SpringServer.domain.Ingredient;
import com.ae.ae_SpringServer.dto.response.IngredientResponseDto;
import com.ae.ae_SpringServer.dto.response.ResResponse;
import com.ae.ae_SpringServer.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import static com.ae.ae_SpringServer.config.BaseResponseStatus.EMPTY_JWT;
import static com.ae.ae_SpringServer.config.BaseResponseStatus.INVALID_JWT;
import static java.util.stream.Collectors.toList;


@RestController
@RequiredArgsConstructor
public class IngredientApiController {
    private final IngredientService ingredientService;

    //[GET] 9-1 모든 재료 조회
    @GetMapping("/api/ingredient")
    public BaseResponse<ResResponse> ingredients(@AuthenticationPrincipal String userId) {
        if(userId.equals("INVALID JWT")){
            return new BaseResponse<>(INVALID_JWT);
        }
        if(userId == null) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        List<Ingredient> findIngredients = ingredientService.findAllIngredients();
        List<IngredientResponseDto> collect = findIngredients.stream()
                .map(m -> new IngredientResponseDto(m.getId(), m.getName()))
                .collect(toList());
        return new BaseResponse<>(new ResResponse(collect.size(), collect));
    }
}
