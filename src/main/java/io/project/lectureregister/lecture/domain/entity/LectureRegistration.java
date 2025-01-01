package io.project.lectureregister.lecture.domain.entity;

import io.project.lectureregister.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "LECTURE_REGISTRATION",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "UQ_USER_LEC",
            columnNames = {"LECTURE_ID", "USER_ID"}
        )
    }
)
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

    @Builder
    private LectureRegistration(Lecture lecture, User user, LocalDateTime regDt) {
        this.lecture = lecture;
        this.user = user;
        this.regDt = regDt;
    }

    public static LectureRegistration create(Lecture lecture, User user) {
        return LectureRegistration.builder()
                .lecture(lecture)
                .user(user)
                .regDt(LocalDateTime.now())
                .build();
    }
}
