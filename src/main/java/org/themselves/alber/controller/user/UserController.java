package org.themselves.alber.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.themselves.alber.config.exception.CustomException;
import org.themselves.alber.config.exception.ErrorCode;
import org.themselves.alber.domain.User;
import org.themselves.alber.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user/new")
    public Boolean joinUser(@RequestBody @Valid UserJoinDto userJoinDto) {

        if (!userJoinDto.getPassword().equals(userJoinDto.getPasswordCheck()))
            throw new CustomException(ErrorCode.PASSWORD_PASSWORDCHECK_ALONG);

        User user = new User();
        user.setNickname(userJoinDto.getNickname());
        user.setEmail(userJoinDto.getEmail());
        user.setPassword(userJoinDto.getPassword());

        return userService.JoinUser(user);
    }

    @GetMapping("/user/{id}")
    public UserDto getUserOne(@PathVariable("id") Long id) {

        User user = userService.getUserOne(id).get();
        return new UserDto(user.getId(), user.getNickname(), user.getEmail(), user.getStatus(), user.getLastLoginDate(), user.getJoinedDate());
    }

    @GetMapping("/userlist")
    public List<UserDto> getUserAll() {

        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userService.getUserAll())
            userDtoList.add(new UserDto(user.getId(), user.getNickname(), user.getEmail(), user.getStatus(), user.getLastLoginDate(), user.getJoinedDate()));

        return userDtoList;
    }

    @PutMapping("/user/{id}")
    public UserDto updateUser(@PathVariable("id") Long id, @RequestBody UserUpdateDto userUpdateDto) {

        User user = new User();
        user.setId(id);
        user.setNickname(userUpdateDto.getNickname());
        user.setEmail(userUpdateDto.getEmail());
        user.setPassword(userUpdateDto.getPassword());

        User responseUser = userService.updateUser(user);
        return new UserDto(responseUser.getId(), responseUser.getNickname(), responseUser.getEmail(), responseUser.getStatus(), responseUser.getLastLoginDate(), responseUser.getJoinedDate());
    }

    @DeleteMapping("/user/{id}")
    public Boolean deleteUser(@PathVariable("id") Long id) {

        return userService.deleteUser(id);
    }

    @GetMapping("/login")
    private Boolean login(@RequestBody UserLoginDto userLoginDto ) {

        User user = new User();
        user.setEmail(userLoginDto.getEmail());
        user.setPassword(userLoginDto.getPassword());

        userService.login(user);

        return true;

    }

    @GetMapping("/logout")
    private Boolean logout() {

        userService.logout();

        return true;

    }
}
