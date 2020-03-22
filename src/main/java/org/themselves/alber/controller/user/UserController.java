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
import org.themselves.alber.controller.user.dto.*;
import org.themselves.alber.domain.User;
import org.themselves.alber.domain.common.UserType;
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

        userService.joinUser(userJoinDto, UserType.USER);

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

        User updateUser = userService.updateUser(userUpdateDto, principal.getName());

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


    @GetMapping(value = "/nickname", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "닉네임 중복확인")
    public ResponseEntity<ResponseContent<Boolean>> duplicationCheckNickname(@RequestBody UserNicknameDto userNicknameDto) {
        boolean isExist = userService.existUserNickname(userNicknameDto.getNickname());
        return ResponseEntity
                .ok()
                .body(new ResponseContent<>(StatusCode.SUCCESS, isExist));
    }


    @GetMapping(value = "/email", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "이메일 중복확인")
    public ResponseEntity<ResponseContent<Boolean>> duplicationCheckEmail(@RequestBody UserEmailDto userEmailDto) {
        boolean isExist = userService.existUserEmail(userEmailDto.getEmail());
        return ResponseEntity
                .ok()
                .body(new ResponseContent<>(StatusCode.SUCCESS, isExist));
    }

}
