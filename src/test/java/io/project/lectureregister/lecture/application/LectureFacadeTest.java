package io.project.lectureregister.lecture.application;

import io.project.lectureregister.lecture.domain.command.LectureRegisterCommand;
import io.project.lectureregister.lecture.domain.entity.Lecture;
import io.project.lectureregister.lecture.domain.service.LectureRegistrationService;
import io.project.lectureregister.lecture.domain.service.LectureService;
import io.project.lectureregister.lecture.interfaces.api.dto.LectureResponse;
import io.project.lectureregister.lecture.interfaces.api.mapper.LectureMapper;
import io.project.lectureregister.lecture.interfaces.api.mapper.LectureMapperImpl;
import io.project.lectureregister.user.domain.entity.User;
import io.project.lectureregister.user.domain.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LectureFacadeTest {

    @Mock
    UserService userService;

    @Mock
    LectureService lectureService;

    @Mock
    LectureRegistrationService lectureRegistrationService;

    @Spy
    LectureMapper lectureMapper = new LectureMapperImpl();

    @InjectMocks
    LectureFacade lectureFacade;

    @DisplayName("특강을 신청한다.")
    @Test
    void register() {
        // given
        User user = User.createUser("PARK", "test@gmail.com", LocalDateTime.now());
        Lecture lecture = Lecture.createLecture("플러스 특강", 0, 30,
                LocalDateTime.of(2025, Month.JANUARY, 7, 15, 0),
                LocalDateTime.of(2025, Month.JANUARY, 7, 18, 0));
        given(userService.getUser(any(Long.class)))
                .willReturn(user);
        given(lectureService.getLectureForUpdate(any(Long.class)))
                .willReturn(lecture);
        LectureRegisterCommand command = LectureRegisterCommand.builder()
                .userId(1L)
                .lectureId(1L)
                .build();

        // when
        lectureFacade.register(command);

        // then
        verify(userService, times(1)).getUser(1L);
        verify(lectureService, times(1)).getLectureForUpdate(1L);
        verify(lectureRegistrationService, times(1)).register(user, lecture);
    }

    @DisplayName("신청 가능한 특강을 조회한다.")
    @Test
    void getAvailableLecture() {
        // given
        int maxCapacity = 30;
        Lecture lecture = Lecture.createLecture("플러스 토요 특강 1주차", 0, maxCapacity,
                LocalDateTime.of(2025, Month.JANUARY, 4, 13, 0),
                LocalDateTime.of(2025, Month.JANUARY, 4, 18, 0));
        Lecture lecture2 = Lecture.createLecture("플러스 토요 특강 2주차", 10, maxCapacity,
                LocalDateTime.of(2025, Month.JANUARY, 11, 13, 0),
                LocalDateTime.of(2025, Month.JANUARY, 11, 18, 0));
        Lecture lecture3 = Lecture.createLecture("플러스 토요 특강 3주차", 29, maxCapacity,
                LocalDateTime.of(2025, Month.JANUARY, 18, 13, 0),
                LocalDateTime.of(2025, Month.JANUARY, 18, 18, 0));
        Lecture lecture4 = Lecture.createLecture("플러스 토요 특강 4주차", 30, maxCapacity,
                LocalDateTime.of(2025, Month.JANUARY, 25, 13, 0),
                LocalDateTime.of(2025, Month.JANUARY, 25, 18, 0));
        given(lectureService.getOpenLectures(any(LocalDateTime.class)))
                .willReturn(List.of(lecture, lecture2, lecture3, lecture4));
        given(lectureRegistrationService.filterAcceptableLectures(any(List.class)))
                .willReturn(List.of(lecture, lecture2, lecture3));

        // when
        List<LectureResponse> response = lectureFacade.getAvailableLectures(LocalDate.now());

        // then
        verify(lectureService, times(1)).getOpenLectures(any(LocalDateTime.class));
        verify(lectureRegistrationService, times(1)).filterAcceptableLectures(any(List.class));
        assertThat(response).hasSize(3)
                .extracting("lectureName", "capacity", "maxCapacity", "startDt", "endDt")
                .containsExactlyInAnyOrder(
                        tuple("플러스 토요 특강 1주차", 0, 30,
                                LocalDateTime.of(2025, Month.JANUARY, 4, 13, 0),
                                LocalDateTime.of(2025, Month.JANUARY, 4, 18, 0)),
                        tuple("플러스 토요 특강 2주차", 10, 30,
                                LocalDateTime.of(2025, Month.JANUARY, 11, 13, 0),
                                LocalDateTime.of(2025, Month.JANUARY, 11, 18, 0)),
                        tuple("플러스 토요 특강 3주차", 29, maxCapacity,
                                LocalDateTime.of(2025, Month.JANUARY, 18, 13, 0),
                                LocalDateTime.of(2025, Month.JANUARY, 18, 18, 0))
                );
    }

    @DisplayName("사용자가 신청 완료한 특강을 조회한다.")
    @Test
    void getRegisteredLectures() {
        // given
        given(userService.getUser(any(Long.class)))
                .willReturn(User.createUser("PARK", "test@gmail.com", LocalDateTime.now()));

        int maxCapacity = 30;
        Lecture lecture = Lecture.createLecture("플러스 토요 특강 1주차", 0, maxCapacity,
                LocalDateTime.of(2025, Month.JANUARY, 4, 13, 0),
                LocalDateTime.of(2025, Month.JANUARY, 4, 18, 0));
        Lecture lecture2 = Lecture.createLecture("플러스 토요 특강 2주차", 10, maxCapacity,
                LocalDateTime.of(2025, Month.JANUARY, 11, 13, 0),
                LocalDateTime.of(2025, Month.JANUARY, 11, 18, 0));
        given(lectureRegistrationService.getRegisteredLectures(any(User.class)))
                .willReturn(List.of(lecture, lecture2));

        // when
        List<LectureResponse> response = lectureFacade.getRegisteredLectures(1L);

        // then
        verify(userService, times(1)).getUser(any(Long.class));
        verify(lectureRegistrationService, times(1)).getRegisteredLectures(any(User.class));
        assertThat(response).hasSize(2)
                .extracting("lectureName", "capacity", "maxCapacity", "startDt", "endDt")
                .containsExactlyInAnyOrder(
                        tuple("플러스 토요 특강 1주차", 0, 30,
                                LocalDateTime.of(2025, Month.JANUARY, 4, 13, 0),
                                LocalDateTime.of(2025, Month.JANUARY, 4, 18, 0)),
                        tuple("플러스 토요 특강 2주차", 10, 30,
                                LocalDateTime.of(2025, Month.JANUARY, 11, 13, 0),
                                LocalDateTime.of(2025, Month.JANUARY, 11, 18, 0))
                );
    }
}