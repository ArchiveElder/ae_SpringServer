package com.ae.ae_SpringServer.api;

import com.ae.ae_SpringServer.domain.Bistro;
import com.ae.ae_SpringServer.service.BistroService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BistroApiController {
    private final BistroService bistroService;

    @PostMapping("/api/bistromiddle")
    public Result middle(@AuthenticationPrincipal String userId, @RequestBody @Valid MiddleRequest request) {
        List<Bistro> bistros = bistroService.getMiddle(request.getWide());
        List<String> middles = new ArrayList<>();

        for(Bistro bistro : bistros) {
            middles.add(bistro.getMiddle());
        }
        return new Result(middles);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    private static class MiddleRequest {
        private String wide;
    }

   
}
