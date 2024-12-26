package io.project.lectureregister.lecture.infrastructure.repository;

import io.project.lectureregister.lecture.domain.entity.Lecture;
import io.project.lectureregister.lecture.domain.entity.LectureRegistration;
import io.project.lectureregister.lecture.domain.repository.ILectureRegistrationRepository;
import io.project.lectureregister.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LectureRegistrationRepository implements ILectureRegistrationRepository {

    private final LectureRegistrationJpaRepository lectureRegistrationJpaRepository;
    private final LectureRegistrationQueryRepository lectureRegistrationQueryRepository;

    @Override
    public LectureRegistration save(LectureRegistration lectureRegistration) {
        return lectureRegistrationJpaRepository.save(lectureRegistration);
    }

    @Override
    public List<LectureRegistration> findByLecture(Lecture lecture) {
        return lectureRegistrationJpaRepository.findByLecture(lecture);
    }

    @Override
    public int countByLecture(Lecture lecture) {
        return lectureRegistrationJpaRepository.countByLecture(lecture);
    }

    @Override
    public List<LectureRegistration> findByUser(User user) {
        return lectureRegistrationQueryRepository.findByUser(user);
    }
}
