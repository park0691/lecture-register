package io.project.lectureregister.lecture.infrastructure.repository;

import io.project.lectureregister.lecture.domain.entity.Lecture;
import io.project.lectureregister.lecture.domain.entity.LectureRegistration;
import io.project.lectureregister.lecture.domain.repository.ILectureRegistrationRepository;
import io.project.lectureregister.user.domain.entity.User;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
    public Optional<LectureRegistration> findByLectureAndUser(Lecture lecture, User user) {
        return lectureRegistrationJpaRepository.findByLectureAndUser(lecture, user);
    }

    @Override
    public List<LectureRegistration> findByUser(User user) {
        return lectureRegistrationQueryRepository.findByUser(user);
    }
}
