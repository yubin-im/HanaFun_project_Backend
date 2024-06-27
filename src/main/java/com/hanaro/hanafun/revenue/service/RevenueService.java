package com.hanaro.hanafun.revenue.service;

import com.hanaro.hanafun.revenue.dto.YearRevenueResDto;

public interface RevenueService {
    YearRevenueResDto yearRevenue(Long userId, Integer year);
}
