package com.hanaro.hanafun.host.controller;

import com.hanaro.hanafun.common.dto.ApiResponse;
import com.hanaro.hanafun.host.dto.CreateHostReqDto;
import com.hanaro.hanafun.host.dto.CreateHostResDto;
import com.hanaro.hanafun.host.service.impl.HostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/host")
public class HostController {

    private final HostServiceImpl hostService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createHost(@AuthenticationPrincipal Long userId, @RequestBody CreateHostReqDto createHostReqDto){
        CreateHostResDto createHostResDto = hostService.createHost(userId, createHostReqDto);
        return ResponseEntity.ok(new ApiResponse(true, "ok", createHostResDto));
    }
}
