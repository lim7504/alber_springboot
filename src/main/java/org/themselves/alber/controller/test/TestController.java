package org.themselves.alber.controller.test;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.themselves.alber.config.JwtTokenProvider;
import org.themselves.alber.config.response.ResponseContent;
import org.themselves.alber.config.response.StatusCode;
import org.themselves.alber.controller.session.UserLoginDto;
import org.themselves.alber.domain.User;
import org.themselves.alber.service.SessionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
