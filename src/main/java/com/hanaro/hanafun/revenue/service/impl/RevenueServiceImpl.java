package com.hanaro.hanafun.revenue.service.impl;

import com.hanaro.hanafun.host.domain.HostEntity;
import com.hanaro.hanafun.host.domain.HostRepository;
import com.hanaro.hanafun.host.exception.HostNotFoundException;
import com.hanaro.hanafun.lesson.domain.LessonEntity;
import com.hanaro.hanafun.lesson.domain.LessonRepository;
import com.hanaro.hanafun.lesson.exception.LessonNotFoundException;
import com.hanaro.hanafun.revenue.domain.RevenueEntity;
import com.hanaro.hanafun.revenue.domain.RevenueRepository;
import com.hanaro.hanafun.revenue.dto.*;
import com.hanaro.hanafun.revenue.exception.RevenueNotFoundException;
import com.hanaro.hanafun.revenue.mapper.RevenueMapper;
import com.hanaro.hanafun.revenue.service.RevenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RevenueServiceImpl implements RevenueService {
    private final HostRepository hostRepository;
    private final LessonRepository lessonRepository;
    private final RevenueRepository revenueRepository;

    @Override
    @Transactional
    public TotalRevenueResDto totalRevenue(Long userId) {
        //호스트 가져오기
        HostEntity hostEntity = hostRepository.findByUserEntityUserId(userId)
                .orElseThrow(() -> new HostNotFoundException());

        //호스트의 강좌 목록 가져오기
        List<LessonEntity> lessonEntityList = lessonRepository.findByHostEntityHostId(hostEntity.getHostId())
                .orElseThrow(() -> new LessonNotFoundException());

        return new TotalRevenueResDto().builder()
                .totalRevenue(lessonEntityList.stream()
                        .mapToLong(lessonEntity -> revenueRepository.totalRevenueByLessonId(lessonEntity))
                        .sum())
                .build();
    }

    @Override
    @Transactional
    public List<MonthRevenueResDto> monthRevenue(Long userId, Integer year, Integer month) {
        //호스트 가져오기
        HostEntity hostEntity = hostRepository.findByUserEntityUserId(userId)
                .orElseThrow(() -> new HostNotFoundException());

        //호스트의 전체 강좌 목록 가져오기
        List<LessonEntity> lessonEntityList = lessonRepository.findByHostEntityHostId(hostEntity.getHostId())
                .orElseThrow(() -> new LessonNotFoundException());

        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDateTime startOfMonth = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(23, 59, 59);

        //강좌마다 해당년월의 매출 하나씩 건지고, collect 합쳐서 해당년월의 전체 매출 리스트 구하기
        List<RevenueEntity> revenueEntityList = lessonEntityList.stream()
                .flatMap(lessonEntity -> revenueRepository.findByLessonEntityAndCreatedDateBetween(lessonEntity, startOfMonth, endOfMonth).stream())
                .collect(Collectors.toList());

        return revenueEntityList.stream()
                .map(RevenueMapper::revenueEntityToMonthRevenueDto)
                .toList();
    }

    @Override
    @Transactional
    public List<LessonRevenueResDto> lessonRevenue(Integer year, Long lessonId) {
        Year searchYear = Year.of(year);
        LocalDateTime startOfYear = searchYear.atMonth(1).atDay(1).atStartOfDay();
        LocalDateTime endOfYear = searchYear.atMonth(12).atEndOfMonth().atTime(23,59,59);

        //강좌의 해당 년 매출 리스트 구하기
        List<RevenueEntity> revenueEntityList = revenueRepository
                .findByLessonEntityLessonIdAndCreatedDateBetween(lessonId, startOfYear, endOfYear)
                .orElseThrow(() -> new RevenueNotFoundException());

        return revenueEntityList.stream()
                .map(RevenueMapper::revenueEntityToLessonRevenueResDto)
                .toList();
    }

    @Override
    @Transactional
    public UpdatePriceResDto updatePrice(UpdatePriceReqDto updatePriceReqDto) {
        LessonEntity lessonEntity = lessonRepository.findById(updatePriceReqDto.getLessonId())
                .orElseThrow(() -> new LessonNotFoundException());

        YearMonth yearMonth = YearMonth.of(updatePriceReqDto.getYear(), updatePriceReqDto.getMonth());
        LocalDateTime startOfMonth = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(23, 59, 59);

        RevenueEntity revenueEntity = revenueRepository
                .findByLessonEntityAndCreatedDateBetween(lessonEntity, startOfMonth, endOfMonth)
                .orElseThrow(() -> new RevenueNotFoundException());
        revenueEntity.setMaterialPrice(updatePriceReqDto.getMaterialPrice());
        revenueEntity.setRentalPrice(updatePriceReqDto.getRentalPrice());
        revenueEntity.setEtcPrice(updatePriceReqDto.getEtcPrice());

        return new UpdatePriceResDto().builder()
                .lessonId(revenueEntity.getLessonEntity().getLessonId())
                .materialPrice(revenueEntity.getMaterialPrice())
                .rentalPrice(revenueEntity.getRentalPrice())
                .etcPrice(revenueEntity.getEtcPrice())
                .build();
    }
}
