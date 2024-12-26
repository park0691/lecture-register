package io.project.lectureregister.lecture.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LectureTest {

    @DisplayName("신청 인원 수를 증가한다.")
    @Test
    void increaseCapacity() {
        // given
        Lecture lecture = Lecture.builder()
                .capacity(11)
                .build();

        // when
        lecture.increaseCapacity();

        // then
        assertThat(lecture.getCapacity()).isEqualTo(12);
    }

    @DisplayName("신청 인원 수가 최대 신청 수보다 작다면 false를 반환한다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 10, 19, 29})
    void isMaxCapacityFalse(int capacity) {
        // given
        Lecture lecture = Lecture.builder()
                .capacity(capacity)
                .maxCapacity(30)
                .build();

        // when
        boolean result = lecture.isMaxCapacity();

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("신청 인원 수가 최대 신청 수보다 크거나 같다면 true를 반환한다.")
    @ParameterizedTest
    @ValueSource(ints = {30, 40, 50, 60})
    void isMaxCapacityTrue(int capacity) {
        // given
        Lecture lecture = Lecture.builder()
                .capacity(capacity)
                .maxCapacity(30)
                .build();

        // when
        boolean result = lecture.isMaxCapacity();

        // then
        assertThat(result).isTrue();
    }
}