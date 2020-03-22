package org.themselves.alber.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.themselves.alber.config.response.CustomException;
import org.themselves.alber.config.response.ResponseContent;
import org.themselves.alber.config.response.StatusCode;
import org.themselves.alber.controller.user.dto.UserDto;
import org.themselves.alber.controller.user.dto.UserDtoByAdmin;
import org.themselves.alber.controller.user.dto.UserJoinDto;
import org.themselves.alber.controller.user.dto.UserUpdateDto;
import org.themselves.alber.domain.User;
import org.themselves.alber.domain.common.UserType;
import org.themselves.alber.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
@Api(description = "회원_어드민")
public class UserAdminController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping(consumes="application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "회원가입")
    public ResponseEntity<ResponseContent> joinUser(@RequestBody @Valid UserJoinDto userJoinDto) {

        userService.joinUser(userJoinDto, UserType.ADMIN);

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
    public ResponseEntity<ResponseContent<List<UserDtoByAdmin>>> getUserAll(Pageable pageable) {

        List<UserDtoByAdmin> userDtoList = new ArrayList<>();
        for (User user : userService.getUserAll(pageable))
            userDtoList.add(modelMapper.map(user, UserDtoByAdmin.class));

        return ResponseEntity
                .ok()
                .body( new ResponseContent<>(StatusCode.SUCCESS, userDtoList));
    }

    @PutMapping(value = "/{id}", consumes="application/json; charset=UTF-8", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "회원수정")
    public ResponseEntity<ResponseContent<UserDtoByAdmin>> updateUser(@PathVariable("id") Long id, @RequestBody UserUpdateDto userUpdateDto) {

        User updateUser = userService.updateUserAdmin(userUpdateDto, id);

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
