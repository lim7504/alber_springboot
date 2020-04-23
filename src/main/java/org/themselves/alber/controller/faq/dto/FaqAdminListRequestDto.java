package org.themselves.alber.controller.faq.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FaqAdminListRequestDto {

    @ApiModelProperty("질문번호")
    private Long id;

    @ApiModelProperty("질문")
    private String question;

    @ApiModelProperty("답변")
    private String answer;


}
