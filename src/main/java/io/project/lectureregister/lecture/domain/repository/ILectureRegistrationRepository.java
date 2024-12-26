package io.project.lectureregister.lecture.domain.repository;

import io.project.lectureregister.lecture.domain.entity.Lecture;
import io.project.lectureregister.lecture.domain.entity.LectureRegistration;
import io.project.lectureregister.user.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface ILectureRegistrationRepository {
    LectureRegistration save(LectureRegistration lectureRegistration);

    Optional<LectureRegistration> findByLectureAndUser(Lecture lecture, User user);

    List<LectureRegistration> findByUser(User user);
}
