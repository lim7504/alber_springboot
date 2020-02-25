package org.themselves.alber.controller.test;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.themselves.alber.config.response.ResponseContent;
import org.themselves.alber.config.response.StatusCode;

@RestController @RequiredArgsConstructor
@Api(description = "test")
public class TestController {

    @GetMapping("/hello")
    @ApiOperation(value = "test")
    private ResponseEntity test() {

        return ResponseEntity
                .ok()
                .body(new ResponseContent(StatusCode.SUCCESS_LOGIN));
    }



}
