package io.project.lectureregister.lecture.domain.repository;

import io.project.lectureregister.lecture.domain.entity.Lecture;
import io.project.lectureregister.lecture.domain.entity.LectureRegistration;
import io.project.lectureregister.user.domain.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ILectureRegistrationRepository {
    LectureRegistration save(LectureRegistration lectureRegistration);

    List<LectureRegistration> findByLecture(Lecture lecture);

    int countByLecture(Lecture lecture);
}
