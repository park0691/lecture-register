package io.project.lectureregister.lecture.infrastructure.repository;

import io.project.lectureregister.lecture.domain.entity.Lecture;
import io.project.lectureregister.lecture.domain.entity.LectureRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRegistrationJpaRepository extends JpaRepository<LectureRegistration, Long> {
    List<LectureRegistration> findByLecture(Lecture lecture);
}
