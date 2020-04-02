package org.themselves.alber.controller.wastebasketcomment;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.themselves.alber.config.response.ResponseContent;
import org.themselves.alber.config.response.StatusCode;
import org.themselves.alber.controller.wastebasketcomment.dto.WastebasketCommentNImageDto;
import org.themselves.alber.domain.User;
import org.themselves.alber.service.UserService;
import org.themselves.alber.service.WastebasketCommentService;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wastebasketcomment")
@Api(description = "댓글")
public class WastebasketCommentController {

    private final WastebasketCommentService wastebasketCommentService;
    private final UserService userService;

    @GetMapping(value = "/mypage", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "마이페이지")
    @ApiIgnore
    public ResponseEntity<ResponseContent<List<WastebasketCommentNImageDto>>> getMyPage(Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        List<WastebasketCommentNImageDto> commentListByUser = wastebasketCommentService.getWastebasketCommentListByUser(user);

        return ResponseEntity
                .ok()
                .body(new ResponseContent<>(StatusCode.SUCCESS, commentListByUser));
    }
}