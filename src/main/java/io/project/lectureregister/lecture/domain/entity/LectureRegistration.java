package io.project.lectureregister.lecture.domain.entity;

import io.project.lectureregister.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "LECTURE_REGISTRATION")
@NoArgsConstructor
@Getter
public class LectureRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LECTURE_REGISTRATION_ID")
    private Long lectureRegistrationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LECTURE_ID")
    private Lecture lecture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "REG_DT")
    private LocalDateTime regDt;
}
