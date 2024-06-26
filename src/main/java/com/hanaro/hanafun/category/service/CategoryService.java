package com.hanaro.hanafun.category.service;

import com.hanaro.hanafun.category.dto.response.CategoryResDto;
import com.hanaro.hanafun.common.dto.ApiResponse;

import java.util.List;

public interface CategoryService {
    ApiResponse<List<CategoryResDto>> categoryList();
}
