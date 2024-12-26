package io.project.lectureregister.lecture.domain.service;

import io.project.lectureregister.global.common.exception.CustomException;
import io.project.lectureregister.global.common.exception.ErrorCode;
import io.project.lectureregister.lecture.domain.entity.Lecture;
import io.project.lectureregister.lecture.domain.entity.LectureRegistration;
import io.project.lectureregister.lecture.domain.repository.ILectureRegistrationRepository;
import io.project.lectureregister.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureRegistrationService {

    private final ILectureRegistrationRepository lectureRegistrationRepository;

    public LectureRegistration register(User user, Lecture lecture) {
        if (lecture.isMaxCapacity()) {
            throw new CustomException(ErrorCode.EXCEED_MAX_LECTURE_CAPACITY);
        }
        
        lecture.increaseCapacity();

        return lectureRegistrationRepository.save(LectureRegistration.create(lecture, user));
    }

    public List<Lecture> filterAcceptableLectures(List<Lecture> lectures) {
        return lectures.stream()
                .filter(lecture -> !lecture.isMaxCapacity())
                .toList();
    }

    public List<Lecture> getRegisteredLectures(User user) {
        return lectureRegistrationRepository.findByUser(user).stream()
                .map(LectureRegistration::getLecture)
                .toList();
    }
}
