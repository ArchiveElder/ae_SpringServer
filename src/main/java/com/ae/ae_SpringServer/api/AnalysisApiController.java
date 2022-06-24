package com.ae.ae_SpringServer.api;

import com.ae.ae_SpringServer.service.AnalysisService;
import com.ae.ae_SpringServer.service.RecordService;
import com.ae.ae_SpringServer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AnalysisApiController {
    private final RecordService recordService;
    private final UserService userService;
    private final AnalysisService analysisService;

    //@GetMapping("api/analysis")

}
