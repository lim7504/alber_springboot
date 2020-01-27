package org.themselves.alber.controller.admin;

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
import org.themselves.alber.controller.user.UserDtoByAdmin;
import org.themselves.alber.controller.user.UserJoinDto;
import org.themselves.alber.controller.user.UserUpdateDto;
import org.themselves.alber.domain.User;
import org.themselves.alber.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@Api(description = "회원_어드민")
public class UserAdminController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping(consumes="application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "회원가입")
    public ResponseEntity<ResponseContent> joinUser(@RequestBody @Valid UserJoinDto userJoinDto) {

        if (!userJoinDto.checkPassword())
        throw new CustomException(StatusCode.PASSWORD_PASSWORDCHECK_ALONG);

        User user = modelMapper.map(userJoinDto, User.class);
        userService.JoinUser(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseContent(StatusCode.SUCCESS_CREATED));
    }

    @GetMapping(value = "/{id}", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "회원상세조회")
    public ResponseEntity<ResponseContent<UserDtoByAdmin>> getUserOne(@PathVariable("id") Long id) {

        User user = userService.getUserOne(id);
        UserDtoByAdmin userDto = modelMapper.map(user, UserDtoByAdmin.class);

        return ResponseEntity
                .ok()
                .body(new ResponseContent<>(StatusCode.SUCCESS, userDto));
    }

    @GetMapping(produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "회원리스트조회")
    public ResponseEntity<ResponseContent<List<UserDtoByAdmin>>> getUserAll() {

        List<UserDtoByAdmin> userDtoList = new ArrayList<>();
        for (User user : userService.getUserAll())
            userDtoList.add(modelMapper.map(user, UserDtoByAdmin.class));

        return ResponseEntity
                .ok()
                .body( new ResponseContent<>(StatusCode.SUCCESS, userDtoList));
    }

    @PutMapping(value = "/{id}", consumes="application/json; charset=UTF-8", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "회원수정")
    public ResponseEntity<ResponseContent<UserDtoByAdmin>> updateUser(@PathVariable("id") Long id, @RequestBody UserUpdateDto userUpdateDto) {

        User user = modelMapper.map(userUpdateDto, User.class);
        user.setId(id);

        User updateUser = userService.updateUser(user);

        UserDtoByAdmin userDto = modelMapper.map(updateUser, UserDtoByAdmin.class);
        return ResponseEntity
                .ok()
                .body( new ResponseContent<>(StatusCode.SUCCESS, userDto));
    }

    @DeleteMapping(value = "/{id}", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "회원삭제")
    public ResponseEntity deleteUser(@PathVariable("id") Long id) {

        userService.deleteUser(id);

        return ResponseEntity
                .ok()
                .body(new ResponseContent(StatusCode.SUCCESS));
    }

}