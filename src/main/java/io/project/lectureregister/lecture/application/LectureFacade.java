package io.project.lectureregister.lecture.application;

import io.project.lectureregister.lecture.domain.command.LectureRegisterCommand;
import io.project.lectureregister.lecture.domain.entity.Lecture;
import io.project.lectureregister.lecture.domain.service.LectureRegistrationService;
import io.project.lectureregister.lecture.domain.service.LectureService;
import io.project.lectureregister.user.domain.entity.User;
import io.project.lectureregister.user.domain.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class LectureFacade {

    private final UserService userService;
    private final LectureService lectureService;
    private final LectureRegistrationService lectureRegistrationService;

    public void register(LectureRegisterCommand command) {
        User user = userService.getUser(command.getUserId());
        Lecture lecture = lectureService.getLecture(command.getLectureId());
        lectureRegistrationService.register(user, lecture);
    }
}

