package io.project.lectureregister.global.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /* 400 BAD_REQUEST : 잘못된 요청 */

    /* 401 UNAUTHORIZED : 인증되지 않은 요청 */

    /* 404 NOT_FOUND : Resource를 찾을 수 없음 */
    USER_NOT_FOUND(NOT_FOUND, "해당 유저의 정보를 찾을 수 없습니다."),
    LECTURE_NOT_FOUND(NOT_FOUND, "특강 정보를 찾을 수 없습니다."),
    EXCEED_MAX_LECTURE_CAPACITY(NOT_FOUND, "특강 신청 수용 최대 인원을 초과하였습니다."),

    /* 409 CONFLICT : Resource의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    APPLY_LECTURE_DUPLICATE(CONFLICT, "이미 특강을 신청한 유저입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
