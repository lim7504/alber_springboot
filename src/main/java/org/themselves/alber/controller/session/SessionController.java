package org.themselves.alber.controller.session;

import io.jsonwebtoken.Jwts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import netscape.security.Principal;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.themselves.alber.config.JwtTokenProvider;
import org.themselves.alber.config.response.ResponseContent;
import org.themselves.alber.config.response.StatusCode;
import org.themselves.alber.domain.User;
import org.themselves.alber.service.SessionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController @RequiredArgsConstructor
@RequestMapping("/session")
@Api(description = "로그인/로그아웃")
public class SessionController {

    private final SessionService sessionService;
    private final ModelMapper modelMapper;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping(consumes="application/json", produces = "application/json")
    @ApiOperation(value = "로그인")
    private ResponseEntity login(@RequestBody UserLoginDto userLoginDto , HttpServletResponse response) {

        User user = modelMapper.map(userLoginDto, User.class);

        User loginUser = sessionService.login(user);
        String token = jwtTokenProvider.createToken(loginUser.getEmail(), loginUser.getType().name());

//        Cookie cookie = new Cookie("X-AUTH-TOKEN", token);
//        cookie.setMaxAge(60*60); //1시간
//        cookie.setPath("/");
//        response.addCookie(cookie);
        //클라이언트에서 쿠키저장하고 API호출 할떄마다 헤더에 토큰 추가

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
        responseHeaders.add("X-AUTH-TOKEN",token);
        return ResponseEntity
                .ok()
                .headers(responseHeaders)
                .body(new ResponseContent(StatusCode.SUCCESS_LOGIN));
    }

    @DeleteMapping(produces = "application/json", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "로그아웃")
    private ResponseEntity logout(HttpServletRequest request, HttpServletResponse response) {

        SecurityContextHolder.getContext().setAuthentication(null);
//        Cookie cookie = new Cookie("X-AUTH-TOKEN", null);
//        cookie.setMaxAge(0);
//        cookie.setPath("/");
//        response.addCookie(cookie);
        //클라이언트에서 쿠키삭제

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
        return ResponseEntity
                .ok()
                .headers(responseHeaders)
                .body(new ResponseContent(StatusCode.SUCCESS));
    }

}
