package com.hanaro.hanafun.revenue.service;

import com.hanaro.hanafun.revenue.dto.totalRevenueResDto;

public interface RevenueService {
    totalRevenueResDto totalRevenue(Long userId);
}
