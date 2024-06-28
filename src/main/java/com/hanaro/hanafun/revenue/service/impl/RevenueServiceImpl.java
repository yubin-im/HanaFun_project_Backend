package com.hanaro.hanafun.revenue.service.impl;

import com.hanaro.hanafun.host.domain.HostEntity;
import com.hanaro.hanafun.host.domain.HostRepository;
import com.hanaro.hanafun.host.exception.HostNotFoundException;
import com.hanaro.hanafun.lesson.domain.LessonEntity;
import com.hanaro.hanafun.lesson.domain.LessonRepository;
import com.hanaro.hanafun.revenue.domain.RevenueRepository;
import com.hanaro.hanafun.revenue.dto.totalRevenueResDto;
import com.hanaro.hanafun.revenue.service.RevenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RevenueServiceImpl implements RevenueService {
    private final HostRepository hostRepository;
    private final LessonRepository lessonRepository;
    private final RevenueRepository revenueRepository;

    @Override
    @Transactional
    public totalRevenueResDto totalRevenue(Long userId) {
        Long yearRevenue;
        HostEntity hostEntity = hostRepository.findByUserEntityUserId(userId).orElseThrow(() -> new HostNotFoundException());

        List<LessonEntity> lessonEntityList = lessonRepository.findByHostEntityHostId(hostEntity.getHostId()).orElseThrow(() -> new HostNotFoundException());
        yearRevenue = lessonEntityList.stream().mapToLong(lessonEntity -> revenueRepository.totalRevenueByLessonId(lessonEntity)).sum();

        return new totalRevenueResDto().builder()
                .yearRevenue(yearRevenue)
                .build();
    }
}
