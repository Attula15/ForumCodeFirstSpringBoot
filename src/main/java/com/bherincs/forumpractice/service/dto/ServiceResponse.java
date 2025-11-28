package com.bherincs.forumpractice.service.dto;

import lombok.Getter;

@Getter
public class ServiceResponse<T> {
    private final boolean isSuccess;
    private final T Data;
    private final String errorMessage;

    public ServiceResponse(T data, String errorMessage) {
        this.isSuccess = data != null;
        Data = data;
        this.errorMessage = errorMessage;
    }
}
