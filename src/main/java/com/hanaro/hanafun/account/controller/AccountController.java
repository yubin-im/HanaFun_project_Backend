package com.hanaro.hanafun.account.controller;

import com.hanaro.hanafun.account.dto.AccountResDto;
import com.hanaro.hanafun.account.dto.PwReqDto;
import com.hanaro.hanafun.account.dto.PwResDto;
import com.hanaro.hanafun.account.service.impl.AccountServiceImpl;
import com.hanaro.hanafun.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountServiceImpl accountService;

    @GetMapping("/list")
    ResponseEntity<ApiResponse> readAccountList(@AuthenticationPrincipal Long userId){
        List<AccountResDto> accountResDtoList = accountService.readAccountList(userId);
        return ResponseEntity.ok(new ApiResponse(true, "ok", accountResDtoList));
    }

    @GetMapping("/pw")
    ResponseEntity<ApiResponse> checkAccountPw(@RequestBody PwReqDto pwReqDto){
        PwResDto pwResDto = accountService.checkAccountPw(pwReqDto);
        return ResponseEntity.ok(new ApiResponse(true, "ok", pwResDto));
    }
}
