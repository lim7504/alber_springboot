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
import org.themselves.alber.controller.faq.dto.FaqAdminListRequestDto;
import org.themselves.alber.controller.faq.dto.FaqAdminListResponseDto;
import org.themselves.alber.controller.faq.dto.FaqDto;
import org.themselves.alber.controller.notifycation.dto.NotifycationAdminListRequestDto;
import org.themselves.alber.controller.notifycation.dto.NotifycationAdminListResponseDto;
import org.themselves.alber.controller.notifycation.dto.NotifycationRequestDto;
import org.themselves.alber.controller.notifycation.dto.NotifycationResponseDto;
import org.themselves.alber.service.FaqService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/faq")
@Api(description = "자주하는질문")
public class FaqAdminController {

    private final FaqService faqService;

    @PostMapping(consumes="application/json; charset=UTF-8", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "자주하는질문 등록")
    public ResponseEntity<ResponseContent> addFaq(@RequestBody @Valid FaqDto dto) {

        faqService.addFaq(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseContent(StatusCode.SUCCESS_CREATED));
    }

    @GetMapping(value = "/{id}", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "자주하는질문 상세조회")
    public ResponseEntity<ResponseContent<FaqDto>> getFaq(@PathVariable("id") Long faq_id) {

        FaqDto responseDto = faqService.getFaq(faq_id);

        return ResponseEntity
                .ok()
                .body(new ResponseContent<>(StatusCode.SUCCESS, responseDto));
    }

    @GetMapping(value = "", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "자주하는질문 리스트조회", notes = "페이징 사용법 = ?page=0&size=5")
    public ResponseEntity<ResponseContent<Page<FaqAdminListResponseDto>>> getFaqList(
            @RequestBody FaqAdminListRequestDto dto,
            Pageable pageable) {

        Page<FaqAdminListResponseDto> responseDto = faqService.getFaqListWithCondition(dto, pageable);

        return ResponseEntity
                .ok()
                .body(new ResponseContent<>(StatusCode.SUCCESS, responseDto));
    }

    @PutMapping(value = "/{id}", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "자주하는질문 수정")
    public ResponseEntity<ResponseContent> setFaq(@PathVariable("id") Long faqId,
                                                             @RequestBody @Valid FaqDto dto) {

        faqService.updateFaq(faqId, dto);

        return ResponseEntity
                .ok()
                .body(new ResponseContent(StatusCode.SUCCESS));
    }

    @DeleteMapping(value = "/{id}", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "자주하는질문 삭제")
    public ResponseEntity<ResponseContent> delFaq(@PathVariable("id") Long faqId) {

        faqService.delFaq(faqId);

        return ResponseEntity
                .ok()
                .body(new ResponseContent(StatusCode.SUCCESS));
    }
}
