package org.themselves.alber.config.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseContent<T> {

    @ApiModelProperty("응답시간")
    private LocalDateTime timestamp = LocalDateTime.now();

    @ApiModelProperty("상태코드")
    private int status = 500;

    @ApiModelProperty("json 데이터")
    private T data;

    @ApiModelProperty("에러코드")
    private String code;

    @ApiModelProperty("에러메세지")
    private String message;

    @ApiModelProperty("에러상세")
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

        @ApiModelProperty("에러필드")
        private String field;

        @ApiModelProperty("에러값")
        private String value;

        @ApiModelProperty("에러사유")
        private String reason;

        public FieldError(org.springframework.validation.FieldError fieldError) {
            this.field = fieldError.getField();
            this.value = fieldError.getDefaultMessage();
            this.reason = (String) fieldError.getRejectedValue();
        }
    }


}