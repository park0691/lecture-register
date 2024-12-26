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

import static org.assertj.core.api.Assertions.*;
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
        Lecture lecture = Lecture.createLecture("플러스 특강", 0, 30,
                LocalDateTime.of(2025, Month.JANUARY, 7, 15, 0),
                LocalDateTime.of(2025, Month.JANUARY, 7, 18, 0));
        LectureRegistration lectureRegistration = LectureRegistration.create(lecture, user);
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
        int maxCapacity = 30;
        Lecture lecture = Lecture.createLecture("플러스 특강", 30, maxCapacity,
                LocalDateTime.of(2025, Month.JANUARY, 7, 15, 0),
                LocalDateTime.of(2025, Month.JANUARY, 7, 18, 0));

        // when, then
        assertThatThrownBy(() -> lectureRegistrationService.register(
                User.createUser("PARK", "test@gmail.com", LocalDateTime.now()),
                lecture
        ))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.EXCEED_MAX_LECTURE_CAPACITY);
    }

    @DisplayName("특강 리스트에서 신청 최대 정원 수를 초과하지 않은 특강들을 반환한다.")
    @Test
    void filterAcceptableLectures() {
        // given
        int maxCapacity = 30;
        List<Lecture> lectures = List.of(
                Lecture.createLecture("플러스 토요 특강 1주차", 0, maxCapacity,
                        LocalDateTime.of(2025, Month.JANUARY, 4, 13, 0),
                        LocalDateTime.of(2025, Month.JANUARY, 4, 18, 0)
                ),
                Lecture.createLecture("플러스 토요 특강 2주차", 10, maxCapacity,
                        LocalDateTime.of(2025, Month.JANUARY, 11, 13, 0),
                        LocalDateTime.of(2025, Month.JANUARY, 11, 18, 0)
                ),
                Lecture.createLecture("플러스 토요 특강 3주차", 29, maxCapacity,
                        LocalDateTime.of(2025, Month.JANUARY, 18, 13, 0),
                        LocalDateTime.of(2025, Month.JANUARY, 18, 18, 0)
                ),
                Lecture.createLecture("플러스 토요 특강 4주차", 30, maxCapacity,
                        LocalDateTime.of(2025, Month.JANUARY, 25, 13, 0),
                        LocalDateTime.of(2025, Month.JANUARY, 25, 18, 0)
                )
        );

        // when
        List<Lecture> acceptableLectures = lectureRegistrationService.filterAcceptableLectures(lectures);

        // then
        assertThat(acceptableLectures).isNotNull();
        assertThat(acceptableLectures).hasSize(3)
                .extracting("name", "capacity", "maxCapacity", "startDt", "endDt")
                .containsExactlyInAnyOrder(
                        tuple("플러스 토요 특강 1주차", 0, maxCapacity,
                                LocalDateTime.of(2025, Month.JANUARY, 4, 13, 0),
                                LocalDateTime.of(2025, Month.JANUARY, 4, 18, 0)),
                        tuple("플러스 토요 특강 2주차", 10, maxCapacity,
                                LocalDateTime.of(2025, Month.JANUARY, 11, 13, 0),
                                LocalDateTime.of(2025, Month.JANUARY, 11, 18, 0)),
                        tuple("플러스 토요 특강 3주차", 29, maxCapacity,
                                LocalDateTime.of(2025, Month.JANUARY, 18, 13, 0),
                                LocalDateTime.of(2025, Month.JANUARY, 18, 18, 0))
                );
    }

    @DisplayName("사용자가 신청한 특강을 조회한다.")
    @Test
    void getRegisteredLectures() {
        // given
        User user = User.createUser("PARK1", "test1@gmail.com", LocalDateTime.now());

        int maxCapacity = 30;
        Lecture lecture = Lecture.createLecture("플러스 토요 특강 1주차",1, maxCapacity,
                LocalDateTime.of(2025, Month.JANUARY, 4, 13, 0),
                LocalDateTime.of(2025, Month.JANUARY, 4, 18, 0));
        Lecture lecture2 = Lecture.createLecture("플러스 토요 특강 2주차", 1, maxCapacity,
                LocalDateTime.of(2025, Month.JANUARY, 11, 13, 0),
                LocalDateTime.of(2025, Month.JANUARY, 11, 18, 0));

        List<LectureRegistration> lectureRegistrations = new ArrayList<>();
        lectureRegistrations.add(LectureRegistration.create(lecture, user));
        lectureRegistrations.add(LectureRegistration.create(lecture2, user));

        given(lectureRegistrationRepository.findByUser(any(User.class)))
                .willReturn(lectureRegistrations);

        // when
        List<Lecture> registeredLectures = lectureRegistrationService.getRegisteredLectures(user);

        // then
        assertThat(registeredLectures).isNotNull();
        assertThat(registeredLectures).hasSize(2)
                .extracting("name", "capacity", "maxCapacity", "startDt", "endDt")
                .containsExactlyInAnyOrder(
                        tuple("플러스 토요 특강 1주차", 1, maxCapacity,
                                LocalDateTime.of(2025, Month.JANUARY, 4, 13, 0),
                                LocalDateTime.of(2025, Month.JANUARY, 4, 18, 0)),
                        tuple("플러스 토요 특강 2주차", 1, maxCapacity,
                                LocalDateTime.of(2025, Month.JANUARY, 11, 13, 0),
                                LocalDateTime.of(2025, Month.JANUARY, 11, 18, 0))
                );
    }
}