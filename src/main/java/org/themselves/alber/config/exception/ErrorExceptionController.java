package org.themselves.alber.config.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice(basePackages = {"org.themselves.alber.controller"})
public class ErrorExceptionController {

    private static final Logger log = LoggerFactory.getLogger(ErrorExceptionController.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected ErrorResponse defaultException(Exception e) {

        log.error(e.getMessage());
        return new ErrorResponse(e.getMessage());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        log.error(e.getMessage());
        final BindingResult bindingResult = e.getBindingResult();

        List<ErrorResponse.FieldError> list = new ArrayList<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            list.add(new ErrorResponse.FieldError(error));
        }

        return new ErrorResponse(ErrorCode.INPUT_VALUE_INVALID, list);
    }

    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected ErrorResponse handleMethodCustomException(CustomException e) {

        log.error(e.getMessage());
        return new ErrorResponse(e.getErrorCode());
    }

}