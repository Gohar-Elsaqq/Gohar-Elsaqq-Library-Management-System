package com.library.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SuccessResponse<T> {
    private String message;
    private Boolean success;
    private T details;
    private LocalDateTime dateTime;

    public SuccessResponse() {
        this.success = Boolean.TRUE;
        this.dateTime = LocalDateTime.now();
    }

    public SuccessResponse(String message, T details) {
        this.message = message;
        this.details = details;
        this.dateTime = LocalDateTime.now();
        this.success = Boolean.TRUE;
    }
}
