package io.project.lectureregister.lecture.domain.service;

import io.project.lectureregister.global.common.exception.CustomException;
import io.project.lectureregister.global.common.exception.ErrorCode;
import io.project.lectureregister.lecture.domain.entity.Lecture;
import io.project.lectureregister.lecture.domain.entity.LectureRegistration;
import io.project.lectureregister.lecture.infrastructure.repository.LectureRegistrationRepository;
import io.project.lectureregister.user.domain.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LectureRegistrationServiceTest {

    @Mock
    LectureRegistrationRepository lectureRegistrationRepository;

    @InjectMocks
    LectureRegistrationService lectureRegistrationService;

    @DisplayName("특강, 유저 정보로 특강을 신청한다.")
    @Test
    void register() {
        // given
        User user = User.createUser("PARK", "test@gmail.com", LocalDateTime.now());
        Lecture lecture = Lecture.createLecture("", 30,
                LocalDateTime.of(2025, Month.JANUARY, 7, 15, 0),
                LocalDateTime.of(2025, Month.JANUARY, 7, 18, 0));
        LectureRegistration lectureRegistration = LectureRegistration.create(lecture, user);
        given(lectureRegistrationRepository.findByLecture(any(Lecture.class)))
                .willReturn(List.of());
        given(lectureRegistrationRepository.save(any(LectureRegistration.class)))
                .willReturn(lectureRegistration);

        // when
        LectureRegistration executedLectureRegistration = lectureRegistrationService.register(user, lecture);

        // then
        assertThat(executedLectureRegistration).isNotNull();
        assertThat(executedLectureRegistration).usingRecursiveComparison()
                .isEqualTo(lectureRegistration);
    }

    @DisplayName("특강 신청 최대 정원 수를 초과하면 예외가 발생해야 한다.")
    @Test
    void registerWithExceedMaxCapacity() {
        // given
        int maxApplyCapacity = 30;
        Lecture lecture = Lecture.createLecture("", maxApplyCapacity,
                LocalDateTime.of(2025, Month.JANUARY, 7, 15, 0),
                LocalDateTime.of(2025, Month.JANUARY, 7, 18, 0));

        List<LectureRegistration> lectureRegistrations = new ArrayList<>();
        for (int i = 0; i < maxApplyCapacity; i++) {
            lectureRegistrations.add(LectureRegistration.create(
                    lecture, User.createUser("PARK" + i, "test" + i + "@gmail.com", LocalDateTime.now()))
            );
        }

        given(lectureRegistrationRepository.findByLecture(any(Lecture.class)))
                .willReturn(lectureRegistrations);

        // when, then
        assertThatThrownBy(() -> lectureRegistrationService.register(
                User.createUser("PARK", "test@gmail.com", LocalDateTime.now()),
                lecture
        ))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.EXCEED_MAX_LECTURE_CAPACITY);
    }
}