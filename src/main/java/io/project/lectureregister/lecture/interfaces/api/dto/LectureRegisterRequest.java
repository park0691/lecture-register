package io.project.lectureregister.lecture.interfaces.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LectureRegisterRequest {
    private Long userId;
    private Long lectureId;

    @Builder
    private LectureRegisterRequest(Long userId, Long lectureId) {
        this.userId = userId;
        this.lectureId = lectureId;
    }
}
