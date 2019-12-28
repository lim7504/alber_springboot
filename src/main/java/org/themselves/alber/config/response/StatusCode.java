package org.themselves.alber.config.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @ToString
public enum StatusCode {

    //Account
    SUCCESS_CREATED("AC_001", "가입에 성공하였습니다.", 201),
    ACCOUNT_NOT_FOUND("AC_002", "해당 회원을 찾을 수 없습니다.", 400),
    EMAIL_DUPLICATION("AC_003", "이메일이 중복되었습니다.", 400),
    NICKNAME_DUPLICATION("AC_004", "닉네임이 중복되었습니다.", 400),
    PASSWORD_PASSWORDCHECK_ALONG("AC_005", "비밀번호와 비밀번호 확인이 같지 않습니다.", 400),
    PASSWORD_ALONG("AC_006", "비밀번호가 맞지 않습니다..", 400),
    DELETE_FAIL("AC_007", "삭제에 실패하였습니다.", 400),

    //Session
    SUCCESS_LOGIN("SS_001", "로그인에 성공하였습니다.", 200),
    FAIL_LOGIN("SS_001", "로그인에 실패하였습니다.", 400),

    //Common Message
    SUCCESS("CM_001", "성공하였습니다.", 200),
    INPUT_VALUE_INVALID("CM_002", "입력값이 올바르지 않습니다.", 400),
    INTERNAL_SERVER_ERROR("CM_003", "서버에러 입니다. 관리자에게 문의해 주세요", 500);

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


