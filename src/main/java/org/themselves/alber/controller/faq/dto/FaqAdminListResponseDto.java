package org.themselves.alber.controller.faq.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FaqAdminListResponseDto {

    @ApiModelProperty("질문번호")
    private Long id;

    @ApiModelProperty("질문")
    private String question;

    @ApiModelProperty("답변")
    private String answer;

    @ApiModelProperty("등록자 Nickname")
    private String userNickname;

    @ApiModelProperty("등록일시")
    private LocalDateTime createdDate;

}
