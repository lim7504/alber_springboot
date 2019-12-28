package org.themselves.alber.config.response;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

    private StatusCode statusCode;

    public CustomException(StatusCode statusCode) {
        super(statusCode.getMessage());
        this.statusCode = statusCode;
    }


}
