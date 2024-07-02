package com.hanaro.hanafun.category.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResDto {
    private Long categoryId;
    private String categoryName;
}
