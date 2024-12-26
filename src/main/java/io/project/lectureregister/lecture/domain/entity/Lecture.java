package io.project.lectureregister.lecture.domain.entity;

import io.project.lectureregister.user.domain.entity.Teacher;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "LECTURE")
@NoArgsConstructor
@Getter
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LECTURE_ID", updatable = false)
    private Long lectureId;

    @Column(name = "NAME")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEACHER_ID")
    private Teacher teacher;

    @Column(name = "MAX_APPLY_CAPACITY")
    private Integer maxApplyCapacity;

    @Column(name = "START_DT")
    private LocalDateTime startDt;

    @Column(name = "END_DT")
    private LocalDateTime endDt;

    @Builder
    private Lecture(Long lectureId, String name, Teacher teacher, Integer maxApplyCapacity, LocalDateTime startDt, LocalDateTime endDt) {
        this.lectureId = lectureId;
        this.name = name;
        this.teacher = teacher;
        this.maxApplyCapacity = maxApplyCapacity;
        this.startDt = startDt;
        this.endDt = endDt;
    }

    public static Lecture createLecture(String name, Integer maxApplyCapacity,
                                  LocalDateTime startDt, LocalDateTime endDt) {
        return Lecture.builder()
                .name(name)
                .maxApplyCapacity(maxApplyCapacity)
                .startDt(startDt)
                .endDt(endDt)
                .build();
    }

    public boolean isMaxCapacity(int capacity) {
        return maxApplyCapacity <= capacity;
    }
}
