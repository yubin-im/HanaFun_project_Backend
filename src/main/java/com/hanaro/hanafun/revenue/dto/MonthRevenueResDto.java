package com.hanaro.hanafun.revenue.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MonthRevenueResDto {

    private Long lessonId;
    private String title;
    private Long revenue;

}
