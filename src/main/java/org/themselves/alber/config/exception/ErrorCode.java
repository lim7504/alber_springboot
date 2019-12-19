package org.themselves.alber.config.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    //Account
    ACCOUNT_NOT_FOUND("AC_001", "해당 회원을 찾을 수 없습니다.", 404),
    EMAIL_DUPLICATION("AC_002", "이메일이 중복되었습니다.", 400),
    NICKNAME_DUPLICATION("AC_003", "닉네임이 중복되었습니다.", 400),
    PASSWORD_PASSWORD_ALONG("AC_004", "비밀번호와 비밀번호 확인이 같지 않습니다.", 400),
    PASSWORD_ALONG("AC_004", "비밀번호가 맞지 않습니다..", 400),

    //Common Message
    INPUT_VALUE_INVALID("CM_001", "입력값이 올바르지 않습니다.", 400);

    private final String code;
    private final String message;
    private final int status;

    ErrorCode(String code, String message, int status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
