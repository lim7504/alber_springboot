package org.themselves.alber.config.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseContent<T> {

    private LocalDateTime timestamp = LocalDateTime.now();
    private int status = 500;
    private T data;
    private String code;
    private String message;
    private List<FieldError> remark;

    public ResponseContent(StatusCode statusCode, List<FieldError> errors) {
        this.message = statusCode.getMessage();
        this.code = statusCode.getCode();
        this.status = statusCode.getStatus();
        this.remark = errors;
    }

    public ResponseContent(StatusCode statusCode) {
        this.message = statusCode.getMessage();
        this.code = statusCode.getCode();
        this.status = statusCode.getStatus();
    }

    public ResponseContent(StatusCode statusCode, T data) {
        this.message = statusCode.getMessage();
        this.code = statusCode.getCode();
        this.status = statusCode.getStatus();
        this.data = data;
    }

    public ResponseContent(String message) {
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