package com.hanaro.hanafun.revenue.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LessonRevenueResDto {
    private Integer month;
    private Long lessonId;
    private String title;
    private Long revenue;
    private Integer materialPrice;
    private Integer rentalPrice;
    private Integer etcPrice;
}
