package com.hanaro.hanafun.category.service.impl;

import com.hanaro.hanafun.category.domain.CategoryEntity;
import com.hanaro.hanafun.category.domain.CategoryRepository;
import com.hanaro.hanafun.category.dto.response.CategoryResDto;
import com.hanaro.hanafun.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    // 카테고리 전체 출력
    @Transactional
    @Override
    public List<CategoryResDto> categoryList() {
        List<CategoryEntity> categoryEntities = categoryRepository.findAll();

        List<CategoryResDto> categoryResDtoList = categoryEntities.stream()
                .map(categoryEntity -> {
                    CategoryResDto categoryResDto = CategoryResDto.builder()
                            .categoryId(categoryEntity.getCategoryId())
                            .categoryName(categoryEntity.getCategoryName())
                            .build();
                    return categoryResDto;
                })
                .collect(Collectors.toList());

        return categoryResDtoList;
    }
}
