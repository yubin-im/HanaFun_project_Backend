package com.hanaro.hanafun.category.controller;

import com.hanaro.hanafun.category.dto.response.CategoryResDto;
import com.hanaro.hanafun.category.service.CategoryService;
import com.hanaro.hanafun.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/category")
    public ResponseEntity<ApiResponse> categoryList() {
        List<CategoryResDto> categoryResDtoList = categoryService.categoryList();
        return ResponseEntity.ok(new ApiResponse<>(true, "ok", categoryResDtoList));
    }
}
