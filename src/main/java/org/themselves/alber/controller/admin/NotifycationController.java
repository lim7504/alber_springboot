package org.themselves.alber.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.themselves.alber.config.response.ResponseContent;
import org.themselves.alber.config.response.StatusCode;
import org.themselves.alber.controller.notifycation.dto.NotifycationDto;
import org.themselves.alber.controller.wastebasket.dto.WastebasketUpdateDto;
import org.themselves.alber.domain.Notifycation;
import org.themselves.alber.domain.User;
import org.themselves.alber.domain.Wastebasket;
import org.themselves.alber.service.NotifycationService;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/notifycation")
@Api(description = "공지사항")
public class NotifycationController {

    private final NotifycationService notifycationService;
    private final ModelMapper modelMapper;

    @PostMapping(consumes="application/json; charset=UTF-8", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "공지사항 등록")
    public ResponseEntity<ResponseContent> addNotifycation(@RequestBody @Valid NotifycationDto notiDto) {

        notifycationService.addNotifycation(notiDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseContent(StatusCode.SUCCESS_CREATED));
    }

    @GetMapping(value = "/{id}", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "공지사항 조회")
    public ResponseEntity<ResponseContent<NotifycationDto>> getNotifycation(@PathVariable("id") Long id) {

        Notifycation notifycation = notifycationService.getNotifycation(id);
        NotifycationDto notifycationDto = modelMapper.map(notifycation, NotifycationDto.class);

        return ResponseEntity
                .ok()
                .body(new ResponseContent<>(StatusCode.SUCCESS, notifycationDto));
    }

    @PutMapping(value = "/{id}", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "공지사항 수정")
    public ResponseEntity<ResponseContent> setNotifycation(@PathVariable Long id,
                                                             @RequestBody @Valid NotifycationDto notiDto
            , Principal principal) {

        notifycationService.updateNotifycation(id, notiDto);

        return ResponseEntity
                .ok()
                .body(new ResponseContent(StatusCode.SUCCESS));
    }

}
