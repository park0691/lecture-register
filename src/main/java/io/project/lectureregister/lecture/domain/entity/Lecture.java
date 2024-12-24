package io.project.lectureregister.lecture.domain.entity;

import io.project.lectureregister.user.domain.entity.Teacher;
import jakarta.persistence.*;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEACHER_ID")
    private Teacher teacher;

    @Column(name = "MAX_APPLY_CNT")
    private Integer maxApplyCnt;

    @Column(name = "APPLY_START_DT")
    private LocalDateTime applyStartDt;

    @Column(name = "APPLY_END_DT")
    private LocalDateTime applyEndDt;

    @Column(name = "START_DT")
    private LocalDateTime startDt;

    @Column(name = "END_DT")
    private LocalDateTime endDt;
}
