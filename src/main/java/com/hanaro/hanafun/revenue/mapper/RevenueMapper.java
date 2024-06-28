package com.hanaro.hanafun.revenue.mapper;

import com.hanaro.hanafun.revenue.domain.RevenueEntity;
import com.hanaro.hanafun.revenue.dto.LessonRevenueResDto;
import com.hanaro.hanafun.revenue.dto.MonthRevenueResDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RevenueMapper {
    public static MonthRevenueResDto revenueEntityToMonthRevenueDto(RevenueEntity revenueEntity){
        if(revenueEntity == null) return  null;

        return MonthRevenueResDto.builder()
                .lessonId(revenueEntity.getLessonEntity().getLessonId())
                .title(revenueEntity.getLessonEntity().getTitle())
                .revenue(revenueEntity.getRevenue())
                .build();
    }

    public static LessonRevenueResDto revenueEntityToLessonRevenueResDto(RevenueEntity revenueEntity){
        if(revenueEntity == null) return null;

        return LessonRevenueResDto.builder()
                .month(revenueEntity.getCreatedDate().getMonthValue())
                .lessonId(revenueEntity.getLessonEntity().getLessonId())
                .title(revenueEntity.getLessonEntity().getTitle())
                .revenue(revenueEntity.getRevenue())
                .materialPrice(revenueEntity.getMaterialPrice())
                .rentalPrice(revenueEntity.getRentalPrice())
                .etcPrice(revenueEntity.getEtcPrice())
                .build();
    }
}
