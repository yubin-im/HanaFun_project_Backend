package com.hanaro.hanafun.revenue.controller;

import com.hanaro.hanafun.common.dto.ApiResponse;
import com.hanaro.hanafun.revenue.dto.YearRevenueResDto;
import com.hanaro.hanafun.revenue.service.impl.RevenueServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/revenue")
public class RevenueController {
    private final RevenueServiceImpl revenueService;
    @GetMapping("/year/{year}")
    public ResponseEntity<ApiResponse> yearRevenue(@AuthenticationPrincipal Long userId, @PathVariable Integer year){
        YearRevenueResDto yearRevenueResDto = revenueService.yearRevenue(userId, year);
        return ResponseEntity.ok(new ApiResponse(true, "ok", yearRevenueResDto));
    }
}
