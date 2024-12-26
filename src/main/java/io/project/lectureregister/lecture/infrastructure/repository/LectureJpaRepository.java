package io.project.lectureregister.lecture.infrastructure.repository;

import io.project.lectureregister.lecture.domain.entity.Lecture;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LectureJpaRepository extends JpaRepository<Lecture, Long> {
    List<Lecture> findByStartDtAfter(LocalDateTime date);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select l from Lecture l where l.lectureId = :id")
    Optional<Lecture> findByIdForUpdate(Long id);
}
