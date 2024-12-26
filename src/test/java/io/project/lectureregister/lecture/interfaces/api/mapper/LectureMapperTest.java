package io.project.lectureregister.lecture.interfaces.api.mapper;

import io.project.lectureregister.lecture.domain.command.LectureRegisterCommand;
import io.project.lectureregister.lecture.domain.entity.Lecture;
import io.project.lectureregister.lecture.interfaces.api.dto.LectureRegisterRequest;
import io.project.lectureregister.lecture.interfaces.api.dto.LectureResponse;
import io.project.lectureregister.user.domain.entity.Teacher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

class LectureMapperTest {

    LectureMapper lectureMapper = Mappers.getMapper(LectureMapper.class);

    @DisplayName("LectureRegisterCommand -> LectureRegisterCommand 변환 테스트")
    @Test
    void toCommand() {
        // given
        LectureRegisterRequest request = LectureRegisterRequest.builder()
                .lectureId(1L)
                .userId(1L)
                .build();

        // when
        LectureRegisterCommand command = lectureMapper.toCommand(request);

        // then
        assertThat(command).usingRecursiveComparison()
                .isEqualTo(request);
    }

    @DisplayName("Lecture -> LectureResponse 변환 테스트")
    @Test
    void toResponse() {
        // given
        Teacher teacher = Teacher.builder()
                .teacherId(9L)
                .name("PROF_01")
                .email("PROF_01@gmail.com")
                .regDt(LocalDateTime.of(2025, Month.JANUARY, 4, 13, 0))
                .build();

        Lecture lecture = Lecture.builder()
                .lectureId(1L)
                .name("플러스 토요 특강 2주차")
                .teacher(teacher)
                .capacity(9)
                .maxCapacity(10)
                .startDt(LocalDateTime.of(2025, Month.JANUARY, 4, 13, 0))
                .endDt(LocalDateTime.of(2025, Month.JANUARY, 4, 18, 0))
                .build();

        // when
        LectureResponse response = lectureMapper.toResponse(lecture);

        // then
        assertThat(response).extracting("lectureId", "lectureName", "teacherId", "teacherName",
                        "teacherEmail", "capacity", "maxCapacity", "startDt", "endDt")
                .containsExactly(
                        1L, "플러스 토요 특강 2주차", 9L, "PROF_01", "PROF_01@gmail.com", 9, 10,
                        LocalDateTime.of(2025, Month.JANUARY, 4, 13, 0),
                        LocalDateTime.of(2025, Month.JANUARY, 4, 18, 0)
                );
    }
}