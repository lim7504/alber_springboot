package org.themselves.alber.controller.faq.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.themselves.alber.domain.Faq;
import org.themselves.alber.domain.Notifycation;

import java.time.LocalDateTime;

@Data
public class FaqListResponseDto {

    @ApiModelProperty("질문 번호")
    private Long id;

    @ApiModelProperty("질문")
    private String question;

    @ApiModelProperty("답변")
    private String answer;

    @ApiModelProperty("등록일시")
    private LocalDateTime createdDate;

    public FaqListResponseDto(Faq faq) {
        this.id = faq.getId();
        this.question = faq.getQuestion();
        this.answer = faq.getAnswer();
        this.createdDate = faq.getCreatedDate();
    }
}
