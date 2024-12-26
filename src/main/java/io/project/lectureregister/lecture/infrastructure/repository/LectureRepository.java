package io.project.lectureregister.lecture.infrastructure.repository;

import io.project.lectureregister.lecture.domain.entity.Lecture;
import io.project.lectureregister.lecture.domain.repository.ILectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LectureRepository implements ILectureRepository {

    private final LectureJpaRepository lectureJpaRepository;

    @Override
    public Optional<Lecture> findBy(Long lectureId) {
        return lectureJpaRepository.findById(lectureId);
    }

    @Override
    public List<Lecture> findByStartDtAfter(LocalDateTime date) {
        return lectureJpaRepository.findByStartDtAfter(date);
    }
}
