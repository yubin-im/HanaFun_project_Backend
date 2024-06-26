package com.hanaro.hanafun.user.controller;

import com.hanaro.hanafun.common.dto.ApiResponse;
import com.hanaro.hanafun.user.dto.LoginReqDto;
import com.hanaro.hanafun.user.dto.LoginResDto;
import com.hanaro.hanafun.user.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/login")
    ResponseEntity<ApiResponse> loginUser(@RequestBody LoginReqDto request) {
        LoginResDto loginResponse = userService.login(request);
        return ResponseEntity.ok(new ApiResponse<LoginResDto>(true, "ok", loginResponse));
    }
}
