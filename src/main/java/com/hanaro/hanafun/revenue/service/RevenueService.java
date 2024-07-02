package com.hanaro.hanafun.revenue.service;

import com.hanaro.hanafun.revenue.dto.*;

import java.util.List;

public interface RevenueService {
    TotalRevenueResDto totalRevenue(Long userId);
    List<MonthRevenueResDto> monthRevenue(Long userId, Integer year, Integer month);
    List<LessonRevenueResDto> lessonRevenue(Integer year, Long lessonId);
    UpdatePriceResDto updatePrice(UpdatePriceReqDto updatePriceReqDto);
}
