package io.project.lectureregister.lecture.interfaces.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LectureResponse {
    private Long lectureId;
    private String lectureName;
    private String teacherName;
    private String teacherEmail;
    private Integer maxApplyCapacity;
    private LocalDateTime startDt;
    private LocalDateTime endDt;
}