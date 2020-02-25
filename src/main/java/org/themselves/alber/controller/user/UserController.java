package org.themselves.alber.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.themselves.alber.config.response.CustomException;
import org.themselves.alber.config.response.ResponseContent;
import org.themselves.alber.config.response.StatusCode;
import org.themselves.alber.controller.user.dto.UserDto;
import org.themselves.alber.controller.user.dto.UserJoinDto;
import org.themselves.alber.controller.user.dto.UserUpdateDto;
import org.themselves.alber.domain.User;
import org.themselves.alber.service.UserService;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Api(description = "회원")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping(consumes="application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "회원가입")
    public ResponseEntity<ResponseContent> joinUser(@RequestBody @Valid UserJoinDto userJoinDto) {

        if (!userJoinDto.checkPassword())
        throw new CustomException(StatusCode.PASSWORD_PASSWORDCHECK_ALONG);

        User user = modelMapper.map(userJoinDto, User.class);
        userService.joinUser(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseContent(StatusCode.SUCCESS_CREATED));
    }

    @GetMapping(value = "/detail", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "회원상세조회")
    public ResponseEntity<ResponseContent<UserDto>> getUserOne(Principal principal) {

        User user = userService.getUserByEmail(principal.getName());
        UserDto userDto = modelMapper.map(user, UserDto.class);

        return ResponseEntity
                .ok()
                .body(new ResponseContent<>(StatusCode.SUCCESS, userDto));
    }

    @PutMapping(value = "/detail", consumes="application/json; charset=UTF-8", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "회원수정")
    public ResponseEntity<ResponseContent<UserDto>> updateUser(@RequestBody UserUpdateDto userUpdateDto, Principal principal) {

        User user = modelMapper.map(userUpdateDto, User.class);
        user.setEmail(principal.getName());

        User updateUser = userService.updateUser(user);

        UserDto userDto = modelMapper.map(updateUser, UserDto.class);
        return ResponseEntity
                .ok()
                .body( new ResponseContent<>(StatusCode.SUCCESS, userDto));
    }

    @DeleteMapping(value = "/detail", consumes="application/json; charset=UTF-8", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "회원탈퇴")
    public ResponseEntity<ResponseContent<UserDto>> deleteUser(Principal principal) {

        userService.exitUser(principal.getName());

        return ResponseEntity
                .ok()
                .body( new ResponseContent<>(StatusCode.SUCCESS));
    }

}
