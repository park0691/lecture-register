package io.project.lectureregister.lecture.application;

import io.project.lectureregister.lecture.domain.command.LectureRegisterCommand;
import io.project.lectureregister.lecture.domain.entity.Lecture;
import io.project.lectureregister.lecture.domain.service.LectureRegistrationService;
import io.project.lectureregister.lecture.domain.service.LectureService;
import io.project.lectureregister.lecture.interfaces.api.dto.LectureResponse;
import io.project.lectureregister.lecture.interfaces.api.mapper.LectureMapper;
import io.project.lectureregister.user.domain.entity.User;
import io.project.lectureregister.user.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LectureFacade {

    private final UserService userService;
    private final LectureService lectureService;
    private final LectureRegistrationService lectureRegistrationService;
    private final LectureMapper lectureMapper;

    public void register(LectureRegisterCommand command) {
        User user = userService.getUser(command.getUserId());
        Lecture lecture = lectureService.getLecture(command.getLectureId());
        lectureRegistrationService.register(user, lecture);
    }

    @Transactional(readOnly = true)
    public List<LectureResponse> getAvailableLectures(LocalDate date) {
        List<Lecture> openLectures = lectureService.getOpenLectures(LocalDateTime.of(date, LocalTime.of(23, 59, 59)));

        return lectureRegistrationService.filterAcceptableLectures(openLectures).stream()
                .map(lectureMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<LectureResponse> getRegisteredLectures(Long userId) {
        User user = userService.getUser(userId);

        return lectureRegistrationService.getRegisteredLectures(user).stream()
                .map(lectureMapper::toResponse)
                .toList();
    }
}

