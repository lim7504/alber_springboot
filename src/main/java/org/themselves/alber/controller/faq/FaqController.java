package org.themselves.alber.controller.faq;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.themselves.alber.config.response.ResponseContent;
import org.themselves.alber.config.response.StatusCode;
import org.themselves.alber.controller.faq.dto.FaqDto;
import org.themselves.alber.controller.faq.dto.FaqListResponseDto;
import org.themselves.alber.controller.notifycation.dto.NotifycationListResponseDto;
import org.themselves.alber.controller.notifycation.dto.NotifycationResponseDto;
import org.themselves.alber.domain.Faq;
import org.themselves.alber.service.FaqService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/faq")
@Api(description = "자주하는질문")
public class FaqController {

    private final FaqService faqService;

    @GetMapping(value = "/{id}", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "자주하는질문 상세조회")
    public ResponseEntity<ResponseContent<FaqDto>> getFaq(@PathVariable("id") Long faqId) {

        FaqDto responseDto = faqService.getFaq(faqId);

        return ResponseEntity
                .ok()
                .body(new ResponseContent<>(StatusCode.SUCCESS, responseDto));
    }

    @GetMapping(value = "", produces = "application/json; charset=UTF-8", headers = "X-AUTH-TOKEN")
    @ApiOperation(value = "자주하는질문 리스트조회", notes = "페이징 사용법 = ?page=0&size=5")
    public ResponseEntity<ResponseContent<Page<FaqListResponseDto>>> getFaqList(
            Pageable pageable) {

        Page<FaqListResponseDto> responseDto = faqService.getFaqList(pageable);

        return ResponseEntity
                .ok()
                .body(new ResponseContent<>(StatusCode.SUCCESS, responseDto));
    }

}
