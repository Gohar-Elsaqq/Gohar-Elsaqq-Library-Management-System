package com.library.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ErrorResponse {
    private String massage;
    private Boolean success;
    private LocalDateTime dateTime;
    private List<String> details;

    public ErrorResponse() {
    }

    public ErrorResponse(String massage, List<String> details) {
        this.massage = massage;
        this.details = details;
        this.dateTime = LocalDateTime.now();
        this.success = Boolean.FALSE;
    }
}
