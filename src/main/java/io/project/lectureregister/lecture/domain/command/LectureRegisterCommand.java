package io.project.lectureregister.lecture.domain.command;

import lombok.Getter;

@Getter
public class LectureRegisterCommand {
    private Long userId;
    private Long lectureId;
}