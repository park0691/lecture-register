package io.project.lectureregister.lecture.domain.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LectureRegisterCommand {
    private Long userId;
    private Long lectureId;
}