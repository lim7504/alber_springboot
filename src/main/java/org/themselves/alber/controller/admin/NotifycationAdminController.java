package org.themselves.alber.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.themselves.alber.config.response.ResponseContent;
import org.themselves.alber.config.response.StatusCode;
import org.themselves.alber.controller.notifycation.dto.NotifycationAdminListRequestDto;
import org.themselves.alber.controller.notifycation.dto.NotifycationAdminListResponseDto;
import org.themselves.alber.controller.notifycation.dto.NotifycationRequestDto;
import org.themselves.alber.controller.notifycation.dto.NotifycationResponseDto;
import org.themselves.alber.service.NotifycationService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/notifycation")
@Api(description = "공지사항")
public class NotifycationAdminController {

    private final NotifycationService notifycationService;

    @PostMapping(consumes="application/json; charset=UTF-8", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "공지사항 등록")
    public ResponseEntity<ResponseContent> addNotifycation(@RequestBody @Valid NotifycationRequestDto notiDto) {

        notifycationService.addNotifycation(notiDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseContent(StatusCode.SUCCESS_CREATED));
    }

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
    public ResponseEntity<ResponseContent<Page<NotifycationAdminListResponseDto>>> getNotifycationList(
            @RequestBody NotifycationAdminListRequestDto notiDto,
            Pageable pageable) {

        Page<NotifycationAdminListResponseDto> responseDto = notifycationService.getNotifycationList(notiDto, pageable);

        return ResponseEntity
                .ok()
                .body(new ResponseContent<>(StatusCode.SUCCESS, responseDto));
    }

    @PutMapping(value = "/{id}", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "공지사항 수정")
    public ResponseEntity<ResponseContent> setNotifycation(@PathVariable("id") Long notiId,
                                                             @RequestBody @Valid NotifycationRequestDto notiDto) {

        notifycationService.updateNotifycation(notiId, notiDto);

        return ResponseEntity
                .ok()
                .body(new ResponseContent(StatusCode.SUCCESS));
    }

    @DeleteMapping(value = "/{id}", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "공지사항 삭제")
    public ResponseEntity<ResponseContent> delWastebasket(@PathVariable("id") Long notiId) {

        notifycationService.deleteNotifycation(notiId);

        return ResponseEntity
                .ok()
                .body(new ResponseContent(StatusCode.SUCCESS));
    }
}
