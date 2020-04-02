package org.themselves.alber.controller.user.dto;
import lombok.Data;
import org.themselves.alber.controller.wastebasketcomment.dto.WastebasketCommentNImageDto;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserMyPageDto {

    private String nickname;
    private String email;
    private String grade;
    private int pinCnt;
    private String url;

    private List<WastebasketCommentNImageDto> comment = new ArrayList<>();

}
