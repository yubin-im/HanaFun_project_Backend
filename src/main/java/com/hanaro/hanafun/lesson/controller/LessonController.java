package com.hanaro.hanafun.lesson.controller;

import com.hanaro.hanafun.common.dto.ApiResponse;
import com.hanaro.hanafun.lesson.dto.request.OpenedLessonsReqDto;
import com.hanaro.hanafun.lesson.dto.response.LessonInfoResDto;
import com.hanaro.hanafun.lesson.dto.response.OpenedLessonsResDto;
import com.hanaro.hanafun.lesson.service.LessonService;
import com.hanaro.hanafun.lesson.service.S3UploadService;
import com.hanaro.hanafun.lessondate.dto.response.LessonDetailResDto;
import com.hanaro.hanafun.lessondate.service.LessonDateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;
    private final LessonDateService lessonDateService;
    private final S3UploadService s3UploadService;

    // 개설 클래스 관리- 개설 클래스 목록 출력
    @GetMapping("/reservation/my/opened")
    public ResponseEntity<ApiResponse> openedLessons(@RequestBody OpenedLessonsReqDto openedLessonsReqDto) {
        List<OpenedLessonsResDto> openedLessons = lessonService.openedLessons(openedLessonsReqDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "ok", openedLessons));
    }

    // 개설 클래스 상세
    @GetMapping("/reservation/my/opened/{lessonId}")
    public ResponseEntity<ApiResponse> lessonDetail(@PathVariable Long lessonId) {
        List<LessonDetailResDto> lessonDetails = lessonDateService.lessonDetail(lessonId);
        return ResponseEntity.ok(new ApiResponse<>(true, "ok", lessonDetails));
    }

    // 클래스 상세보기
    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<ApiResponse> lessonInfo(@PathVariable Long lessonId) {
        LessonInfoResDto lessonInfoResDto = lessonService.lessonInfo(lessonId);
        return ResponseEntity.ok(new ApiResponse<>(true, "ok", lessonInfoResDto));
    }

    // 클래스 이미지 업로드
    @PostMapping("/lesson/image-upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = s3UploadService.saveFile(file);
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("파일 업로드 중 오류가 발생했습니다.");
        }
    }
}
