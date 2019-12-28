package org.themselves.alber.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.themselves.alber.config.response.CustomException;
import org.themselves.alber.config.response.ResponseContent;
import org.themselves.alber.config.response.StatusCode;
import org.themselves.alber.domain.User;
import org.themselves.alber.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Api(description = "회원")
public class UserController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    private final HttpHeaders responseHeaders;

    @PostMapping(consumes="application/json", produces = "application/json")
    @ApiOperation(value = "회원가입")
    public ResponseEntity<ResponseContent> joinUser(@RequestBody @Valid UserJoinDto userJoinDto) {

        if (!userJoinDto.checkPassword())
        throw new CustomException(StatusCode.PASSWORD_PASSWORDCHECK_ALONG);

        User user = modelMapper.map(userJoinDto, User.class);
        userService.JoinUser(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .headers(responseHeaders)
                .body(new ResponseContent(StatusCode.SUCCESS_CREATED));
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @ApiOperation(value = "회원상세조회")
    public ResponseEntity<ResponseContent<UserDto>> getUserOne(@PathVariable("id") Long id) {

        User user = userService.getUserOne(id);
        UserDto userDto = modelMapper.map(user, UserDto.class);

        return ResponseEntity
                .ok()
                .headers(responseHeaders)
                .body(new ResponseContent<>(StatusCode.SUCCESS, userDto));
    }

    @GetMapping(produces = "application/json")
    @ApiOperation(value = "회원리스트조회")
    public ResponseEntity<ResponseContent<List<UserDto>>> getUserAll() {

        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userService.getUserAll())
            userDtoList.add(modelMapper.map(user, UserDto.class));

        return ResponseEntity
                .ok()
                .headers(responseHeaders)
                .body( new ResponseContent<>(StatusCode.SUCCESS, userDtoList));
    }

    @PutMapping(value = "/{id}", consumes="application/json", produces = "application/json")
    @ApiOperation(value = "회원수정")
    public ResponseEntity<ResponseContent<UserDto>> updateUser(@PathVariable("id") Long id, @RequestBody UserUpdateDto userUpdateDto) {

        User user = modelMapper.map(userUpdateDto, User.class);
        user.setId(id);

        User updateUser = userService.updateUser(user);

        UserDto userDto = modelMapper.map(updateUser, UserDto.class);
        return ResponseEntity
                .ok()
                .headers(responseHeaders)
                .body( new ResponseContent<>(StatusCode.SUCCESS, userDto));
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @ApiOperation(value = "회원삭제")
    public ResponseEntity deleteUser(@PathVariable("id") Long id) {

        userService.deleteUser(id);

        return ResponseEntity
                .ok()
                .headers(responseHeaders)
                .body(new ResponseContent(StatusCode.SUCCESS));
    }


}
