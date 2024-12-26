package io.project.lectureregister.lecture.infrastructure.repository;

import io.project.lectureregister.lecture.domain.entity.Lecture;
import io.project.lectureregister.lecture.domain.entity.LectureRegistration;
import io.project.lectureregister.lecture.domain.repository.ILectureRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LectureRegistrationRepository implements ILectureRegistrationRepository {

    private final LectureRegistrationJpaRepository lectureRegistrationJpaRepository;

    @Override
    public LectureRegistration save(LectureRegistration lectureRegistration) {
        return lectureRegistrationJpaRepository.save(lectureRegistration);
    }

    @Override
    public List<LectureRegistration> findByLecture(Lecture lecture) {
        return lectureRegistrationJpaRepository.findByLecture(lecture);
    }
}
