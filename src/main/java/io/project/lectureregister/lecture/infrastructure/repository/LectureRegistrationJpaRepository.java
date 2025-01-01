package io.project.lectureregister.lecture.infrastructure.repository;

import io.project.lectureregister.lecture.domain.entity.Lecture;
import io.project.lectureregister.lecture.domain.entity.LectureRegistration;
import io.project.lectureregister.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LectureRegistrationJpaRepository extends JpaRepository<LectureRegistration, Long> {
    Optional<LectureRegistration> findByLectureAndUser(Lecture lecture, User user);
}
