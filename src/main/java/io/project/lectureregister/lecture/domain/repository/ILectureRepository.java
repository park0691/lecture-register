package io.project.lectureregister.lecture.domain.repository;

import io.project.lectureregister.lecture.domain.entity.Lecture;

import java.util.Optional;

public interface ILectureRepository {
    Optional<Lecture> findBy(Long lectureId);
}
