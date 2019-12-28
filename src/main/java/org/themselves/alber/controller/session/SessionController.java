package org.themselves.alber.controller.session;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.themselves.alber.config.response.ResponseContent;
import org.themselves.alber.config.response.StatusCode;
import org.themselves.alber.domain.User;
import org.themselves.alber.service.UserService;

@RestController @RequiredArgsConstructor
@RequestMapping("/session")
@Api(description = "로그인/로그아웃")
public class SessionController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    private final HttpHeaders responseHeaders;

    @PostMapping(consumes="application/json", produces = "application/json")
    @ApiOperation(value = "로그인")
    private ResponseEntity login(@RequestBody UserLoginDto userLoginDto ) {

        User user = modelMapper.map(userLoginDto, User.class);
        userService.login(user);

        return ResponseEntity
                .ok()
                .headers(responseHeaders)
                .body(new ResponseContent(StatusCode.SUCCESS_LOGIN));
    }

    @DeleteMapping(produces = "application/json")
    @ApiOperation(value = "로그아웃")
    private ResponseEntity logout() {

        userService.logout();

        return ResponseEntity
                .ok()
                .headers(responseHeaders)
                .body(new ResponseContent(StatusCode.SUCCESS));
    }

}
