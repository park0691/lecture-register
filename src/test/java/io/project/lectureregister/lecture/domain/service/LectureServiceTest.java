package io.project.lectureregister.lecture.domain.service;

import io.project.lectureregister.global.common.exception.CustomException;
import io.project.lectureregister.global.common.exception.ErrorCode;
import io.project.lectureregister.lecture.domain.entity.Lecture;
import io.project.lectureregister.lecture.domain.repository.ILectureRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LectureServiceTest {

    @Mock
    private ILectureRepository lectureRepository;

    @InjectMocks
    LectureService lectureService;

    @DisplayName("존재하지 않는 특강 아이디로 특강 정보를 조회하면 예외가 발생한다.")
    @Test
    void getLectureWithInvalidLectureId() {
        // given
        long lectureId = 1L;

        // when, then
        assertThatThrownBy(() -> lectureService.getLecture(lectureId))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.LECTURE_NOT_FOUND);
    }

    @DisplayName("특강 아이디로 특강 정보를 조회한다.")
    @Test
    void getLecture() {
        // given
        long lectureId = 1L;
        Lecture lecture = Lecture.createLecture("플러스 특강", 30,
                LocalDateTime.of(2025, Month.JANUARY, 7, 15, 0),
                LocalDateTime.of(2025, Month.JANUARY, 7, 18, 0));
        given(lectureRepository.findBy(any(Long.class)))
                .willReturn(Optional.of(lecture));

        // when
        Lecture foundLecture = lectureService.getLecture(lectureId);

        // then
        assertThat(foundLecture).usingRecursiveComparison()
                .isEqualTo(lecture);
    }

    @DisplayName("특강 시작 일자가 입력받은 일자 이후에 해당하는 특강을 조회한다.")
    @Test
    void getOpenLectures() {
        // given
        int maxApplyCapacity = 30;
        Lecture lecture = Lecture.createLecture("플러스 토요 특강 1주차", maxApplyCapacity,
                LocalDateTime.of(2025, Month.JANUARY, 4, 13, 0),
                LocalDateTime.of(2025, Month.JANUARY, 4, 18, 0));
        Lecture lecture2 = Lecture.createLecture("플러스 토요 특강 2주차", maxApplyCapacity,
                LocalDateTime.of(2025, Month.JANUARY, 11, 13, 0),
                LocalDateTime.of(2025, Month.JANUARY, 11, 18, 0));

        given(lectureRepository.findByStartDtAfter(any(LocalDateTime.class)))
                .willReturn(List.of(lecture, lecture2));

        // when
        List<Lecture> lectures = lectureService.getOpenLectures(LocalDateTime.now());

        // then
        assertThat(lectures).isNotNull();
        assertThat(lectures).usingRecursiveComparison()
                .isEqualTo(List.of(lecture, lecture2));
    }
}