package com.hanaro.hanafun.revenue.controller;

import com.hanaro.hanafun.common.dto.ApiResponse;
import com.hanaro.hanafun.revenue.dto.*;
import com.hanaro.hanafun.revenue.service.impl.RevenueServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/revenue")
public class RevenueController {
    private final RevenueServiceImpl revenueService;
    @GetMapping("/total")
    public ResponseEntity<ApiResponse> totalRevenue(@AuthenticationPrincipal Long userId){
        TotalRevenueResDto totalRevenueResDto = revenueService.totalRevenue(userId);
        return ResponseEntity.ok(new ApiResponse(true, "ok", totalRevenueResDto));
    }

    @GetMapping("/{year}/{month}")
    public ResponseEntity<ApiResponse> monthRevenue(@AuthenticationPrincipal Long userId, @PathVariable Integer year, @PathVariable Integer month){
        List<MonthRevenueResDto> monthRevenueResDtoList = revenueService.monthRevenue(userId, year, month);
        return ResponseEntity.ok(new ApiResponse(true, "ok", monthRevenueResDtoList));
    }

    @GetMapping("/lesson/{year}/{lessonId}")
    public ResponseEntity<ApiResponse> lessonRevenue(@PathVariable Integer year, @PathVariable Long lessonId){
        List<LessonRevenueResDto> lessonRevenueResDtoList = revenueService.lessonRevenue(year, lessonId);
        return ResponseEntity.ok(new ApiResponse(true, "ok", lessonRevenueResDtoList));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updatePrice(@RequestBody UpdatePriceReqDto updatePriceReqDto){
        UpdatePriceResDto updatePriceResDto = revenueService.updatePrice(updatePriceReqDto);
        return ResponseEntity.ok(new ApiResponse(true, "ok", updatePriceResDto));
    }
}
