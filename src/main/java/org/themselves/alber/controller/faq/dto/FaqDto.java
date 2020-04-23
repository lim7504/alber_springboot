package org.themselves.alber.controller.faq.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.themselves.alber.domain.Faq;

@Data
public class FaqDto {

    @ApiModelProperty("질문")
    private String question;

    @ApiModelProperty("답변")
    private String answer;

    public FaqDto() {
    }

    public FaqDto(Faq faq) {
        this.question = faq.getQuestion();
        this.answer = faq.getAnswer();
    }
}
