package io.project.lectureregister.lecture.domain.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LectureRegisterCommand {
    private Long userId;
    private Long lectureId;

    @Builder
    private LectureRegisterCommand(Long userId, Long lectureId) {
        this.userId = userId;
        this.lectureId = lectureId;
    }
}