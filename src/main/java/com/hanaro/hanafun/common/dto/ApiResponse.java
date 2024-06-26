package com.hanaro.hanafun.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "message", "data"})
public class ApiResponse<T> {
    @JsonProperty("isSuccess")
    private final Boolean isSuccess;

    @JsonProperty("message")
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("data")
    private T data;
}