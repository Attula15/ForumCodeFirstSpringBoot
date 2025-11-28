package com.bherincs.forumpractice.controllers.dto;

import lombok.Getter;

import java.util.Optional;

@Getter
public class ApiResponseDTO<T> {
    private final T data;
    private final boolean isSuccess;
    private final String errorMessage;

    public ApiResponseDTO(T data, String errorMessage) {
        this.data = data;
        this.isSuccess = data != null;
        this.errorMessage = errorMessage;
    }
}
