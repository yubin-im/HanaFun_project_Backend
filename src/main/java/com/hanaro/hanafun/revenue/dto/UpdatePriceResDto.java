package com.hanaro.hanafun.revenue.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePriceResDto {
    private Long lessonId;
    private Integer materialPrice;
    private Integer rentalPrice;
    private Integer etcPrice;
}
