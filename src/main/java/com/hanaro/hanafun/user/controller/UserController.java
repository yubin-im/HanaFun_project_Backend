package com.hanaro.hanafun.user.controller;

import com.hanaro.hanafun.common.dto.ApiResponse;
import com.hanaro.hanafun.user.dto.IsHostResDto;
import com.hanaro.hanafun.user.dto.LoginReqDto;
import com.hanaro.hanafun.user.dto.LoginResDto;
import com.hanaro.hanafun.user.dto.PointResDto;
import com.hanaro.hanafun.user.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/login")
    ResponseEntity<ApiResponse> login(@RequestBody LoginReqDto request) {
        LoginResDto loginResDto = userService.login(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "ok", loginResDto));
    }

    @GetMapping("/point")
    ResponseEntity<ApiResponse> readPoint(@AuthenticationPrincipal Long userId){
        PointResDto pointResDto = userService.readPoint(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "ok", pointResDto));
    }

    @GetMapping("/isHost")
    ResponseEntity<ApiResponse> readIsHost(@AuthenticationPrincipal Long userId){
        IsHostResDto isHostResDto = userService.readIsHost(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "ok", isHostResDto));
    }
}
