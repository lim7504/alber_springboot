package org.themselves.alber.config.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @ToString
public enum StatusCode {

    //Account
    ACCOUNT_NOT_FOUND("AC_002", "해당 회원을 찾을 수 없습니다.", 400),
    EMAIL_DUPLICATION("AC_003", "이메일이 중복되었습니다.", 400),
    NICKNAME_DUPLICATION("AC_004", "닉네임이 중복되었습니다.", 400),
    PASSWORD_PASSWORDCHECK_ALONG("AC_005", "비밀번호와 비밀번호 확인이 같지 않습니다.", 400),
    PASSWORD_ALONG("AC_006", "비밀번호가 맞지 않습니다..", 400),
    DELETE_FAIL("AC_007", "삭제에 실패하였습니다.", 400),

    //Wastebasket
    WASTEBASKET_NOT_FOUND("WB_001", "해당 쓰레기통을 찾을 수 없습니다.", 400),
    WASTEBASKETCOMMENT_NOT_FOUND("WB_002", "해당 댓글을 찾을 수 없습니다.", 400),
    WASTEBASKET_NOT_SAME_USER("WB_003", "해당 쓰레기통의 게시자가 아닙니다.", 400),
    WASTEBASKETCOMMENT_NOT_SAME_USER("WB_004", "해당 댓글의 게시자가 아닙니다.", 400),
    WASTEBASKET_NOT_DELETE_EXIST_COMMENTS("WB_006", "댓글이 존재하여 쓰레기통을 제거 할 수 없습니다.", 400),


    //Session
    SUCCESS_LOGIN("SS_001", "로그인에 성공하였습니다.", 200),
    FAIL_LOGIN("SS_002", "로그인에 실패하였습니다.", 400),

    //Common Message
    SUCCESS("CM_001", "성공하였습니다.", 200),
    SUCCESS_CREATED("CM_002", "등록에 성공하였습니다.", 201),
    INPUT_VALUE_INVALID("CM_003", "입력값이 올바르지 않습니다.", 400),
    FILE_TO_MUCH_ERROR("CM_004", "파일이 너무 많습니다. (최대 3개)", 400),
    FILE_NOT_IMAGE_ERROR("CM_005", "파일이 이미지가 아닙니다. 이미지를 등록하여 주세요.", 400),
    IMAGE_NOT_FOUND("CM_006", "해당 이미지를 찾을 수 없습니다.", 400),
    FILE_CREATE_ERROR("CM_007", "파일생성에러 입니다. 관리자에게 문의해 주세요", 500),
    INTERNAL_SERVER_ERROR("CM_999", "서버에러 입니다. 관리자에게 문의해 주세요", 500);


    private final String code;
    @Setter @Getter
    private String message;
    private final int status;

    StatusCode(String code, String message, int status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}


