package org.themselves.alber.controller.user.dto;
import lombok.Data;
import org.themselves.alber.controller.wastebasketcomment.dto.WastebasketCommentForMyRegistCommentDto;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserMyWastebasketDto {

    private String nickname;
    private String email;
    private String grade;
    private int wastebasketCnt;
    private String url;

    private List<WastebasketCommentForMyRegistCommentDto> comment = new ArrayList<>();

}
