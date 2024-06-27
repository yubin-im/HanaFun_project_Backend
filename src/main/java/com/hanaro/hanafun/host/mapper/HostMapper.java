package com.hanaro.hanafun.host.mapper;

import com.hanaro.hanafun.host.dto.HostLessonDto;
import com.hanaro.hanafun.lesson.domain.LessonEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class HostMapper {
    public HostLessonDto LessonEntityToHostLessonDto(LessonEntity lessonEntity){
        if(lessonEntity == null) return null;

        return new HostLessonDto().builder()
                .lessonId(lessonEntity.getLessonId())
                .title(lessonEntity.getTitle())
                .build();
    }
}
