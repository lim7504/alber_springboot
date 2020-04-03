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
import org.themselves.alber.controller.wastebasketcomment.dto.WastebasketCommentForMyRegistCommentDto;
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


}