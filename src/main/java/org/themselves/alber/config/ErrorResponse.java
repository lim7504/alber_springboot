package org.themselves.alber.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
    private String message;
    private String code;
    private int status;
    private List<FieldError> errors;

    public ErrorResponse(ErrorCode errorCode, List<FieldError> errors) {
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
        this.status = errorCode.getStatus();
        this.errors = errors;
    }

    public ErrorResponse(ErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
        this.status = errorCode.getStatus();
    }

    public ErrorResponse(String message) {
        this.message = message;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        public FieldError(org.springframework.validation.FieldError fieldError) {
            this.field = fieldError.getField();
            this.value = fieldError.getDefaultMessage();
            this.reason = (String) fieldError.getRejectedValue();
        }
    }
}