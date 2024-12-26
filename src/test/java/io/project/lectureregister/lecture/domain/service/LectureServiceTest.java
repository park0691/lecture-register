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
        Lecture lecture = Lecture.createLecture("", 30,
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
}