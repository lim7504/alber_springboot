package org.themselves.alber.controller.notifycation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.themselves.alber.config.response.ResponseContent;
import org.themselves.alber.config.response.StatusCode;
import org.themselves.alber.controller.notifycation.dto.NotifycationAdminListResponseDto;
import org.themselves.alber.controller.notifycation.dto.NotifycationListResponseDto;
import org.themselves.alber.controller.notifycation.dto.NotifycationResponseDto;
import org.themselves.alber.service.NotifycationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifycation")
@Api(description = "공지사항")
public class NotifycationController {

    private final NotifycationService notifycationService;

    @GetMapping(value = "/{id}", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "공지사항 상세조회")
    public ResponseEntity<ResponseContent<NotifycationResponseDto>> getNotifycation(@PathVariable("id") Long notiId) {

        NotifycationResponseDto responseDto = notifycationService.getNotifycation(notiId);

        return ResponseEntity
                .ok()
                .body(new ResponseContent<>(StatusCode.SUCCESS, responseDto));
    }

    @GetMapping(value = "", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "공지사항 리스트조회", notes = "페이징 사용법 = ?page=0&size=5")
    public ResponseEntity<ResponseContent<Page<NotifycationListResponseDto>>> getNotifycationList(
            Pageable pageable) {

        Page<NotifycationListResponseDto> responseDto = notifycationService.getNotifycationList(pageable);

        return ResponseEntity
                .ok()
                .body(new ResponseContent<>(StatusCode.SUCCESS, responseDto));
    }

}
