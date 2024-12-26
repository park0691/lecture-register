package io.project.lectureregister.lecture.domain.repository;

import io.project.lectureregister.lecture.domain.entity.Lecture;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ILectureRepository {
    Optional<Lecture> findByIdForUpdate(Long lectureId);

    List<Lecture> findByStartDtAfter(LocalDateTime date);
}
