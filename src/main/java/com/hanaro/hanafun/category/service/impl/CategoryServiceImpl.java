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
        List<CategoryResDto> categoryResDtoList = new ArrayList<>();
        List<CategoryEntity> categoryEntities = categoryRepository.findAll();

        for(int i = 0; i < categoryEntities.size(); i++) {
            CategoryResDto categoryResDto = CategoryResDto.builder()
                    .categoryId(categoryEntities.get(i).getCategoryId())
                    .categoryName(categoryEntities.get(i).getCategoryName())
                    .build();
            categoryResDtoList.add(categoryResDto);
        }
        return categoryResDtoList;
    }
}
