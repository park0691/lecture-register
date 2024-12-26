package io.project.lectureregister.lecture.domain.service;

import io.project.lectureregister.global.common.exception.CustomException;
import io.project.lectureregister.global.common.exception.ErrorCode;
import io.project.lectureregister.lecture.domain.entity.Lecture;
import io.project.lectureregister.lecture.domain.repository.ILectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final ILectureRepository lectureRepository;

    public Lecture getLecture(Long id) {
        return lectureRepository.findBy(id)
                .orElseThrow(() -> new CustomException(ErrorCode.LECTURE_NOT_FOUND));
    }

    public List<Lecture> getOpenLectures(LocalDateTime dateTime) {
        return lectureRepository.findByStartDtAfter(dateTime);
    }
}
