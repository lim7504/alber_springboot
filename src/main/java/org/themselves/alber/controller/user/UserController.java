package org.themselves.alber.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.themselves.alber.config.response.ResponseContent;
import org.themselves.alber.config.response.StatusCode;
import org.themselves.alber.controller.user.dto.*;
import org.themselves.alber.domain.User;
import org.themselves.alber.domain.common.UserType;
import org.themselves.alber.service.CommonService;
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
    private final CommonService commonService;

    @PostMapping(consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "회원가입")
    public ResponseEntity<ResponseContent> joinUser(@RequestBody @Valid UserJoinDto userJoinDto) {

        userService.joinUser(userJoinDto, UserType.USER);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseContent(StatusCode.SUCCESS_CREATED));
    }


    @GetMapping(value = "/detail", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "회원상세조회")
    public ResponseEntity<ResponseContent<UserResponseDto>> getUserOne(Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        UserResponseDto userDto = modelMapper.map(user, UserResponseDto.class);

        return ResponseEntity
                .ok()
                .body(new ResponseContent<>(StatusCode.SUCCESS, userDto));
    }

    @GetMapping(value = "/mypage", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "마이페이지")
    public ResponseEntity<ResponseContent<UserMyPageDto>> getUserForMyPage(Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        UserMyPageDto userDto = userService.getUserForMyPage(user);

        return ResponseEntity
                .ok()
                .body(new ResponseContent<>(StatusCode.SUCCESS, userDto));
    }

//    @PutMapping(value = "/detail", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
//    @ApiOperation(value = "회원수정")
//    public ResponseEntity<ResponseContent<UserDto>> updateUser(@RequestBody UserUpdateDto userUpdateDto, Principal principal) {
//
//        User updateUser = userService.updateUser(userUpdateDto, principal.getName());
//
//        UserDto userDto = modelMapper.map(updateUser, UserDto.class);
//        return ResponseEntity
//                .ok()
//                .body(new ResponseContent<>(StatusCode.SUCCESS, userDto));
//    }


    @DeleteMapping(value = "/detail", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "회원탈퇴")
    public ResponseEntity<ResponseContent<UserResponseDto>> deleteUser(Principal principal) {
        userService.exitUser(principal.getName());

        return ResponseEntity
                .ok()
                .body(new ResponseContent<>(StatusCode.SUCCESS));
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

    @PatchMapping(value = "/detail/nickname", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "닉네임 변경")
    public ResponseEntity<ResponseContent> updateNickname(@RequestBody UserNicknameDto userNicknameDto, Principal principal) {
        userService.updateNickname(principal.getName(), userNicknameDto);
        return ResponseEntity
                .ok()
                .body(new ResponseContent(StatusCode.SUCCESS));
    }
    @PatchMapping(value = "/detail/password", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "패스워드 변경")
    public ResponseEntity<ResponseContent> updatePassword(@RequestBody UserPasswordDto userPasswordDto, Principal principal) {
        userService.updatePassword(principal.getName(), userPasswordDto);
        return ResponseEntity
                .ok()
                .body(new ResponseContent(StatusCode.SUCCESS));
    }

    @PostMapping(value = "/detail/image", consumes = "multipart/form-data; charset=UTF-8", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "이미지 수정")
    public ResponseEntity<ResponseContent> updateProfileImage(
            @RequestParam("file") MultipartFile file, Principal principal) {

        Long imageId = commonService.imageFileUpload(file);

        userService.updateImage(principal.getName(), imageId);

        return ResponseEntity
                .ok()
                .body(new ResponseContent<>(StatusCode.SUCCESS));
    }

}
