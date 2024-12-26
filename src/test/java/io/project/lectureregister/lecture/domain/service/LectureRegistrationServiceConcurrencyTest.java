package io.project.lectureregister.lecture.domain.service;

import io.project.lectureregister.global.common.exception.CustomException;
import io.project.lectureregister.lecture.domain.entity.Lecture;
import io.project.lectureregister.lecture.infrastructure.repository.LectureJpaRepository;
import io.project.lectureregister.user.domain.entity.User;
import io.project.lectureregister.user.repository.UserJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class LectureRegistrationServiceConcurrencyTest {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private LectureJpaRepository lectureJpaRepository;

    @Autowired
    private LectureRegistrationService lectureRegistrationService;

    @DisplayName("최대 정원이 30명인 특강에 동시에 40명이 신청했을 때, 30명만 성공하고 나머지는 실패해야 한다.")
    @Test
    void registerConcurrently() throws Exception {
        // given
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 40; i++)
            users.add(User.createUser("PARK" + i, "test" + i + "@gmail.com", LocalDateTime.now()));
        userJpaRepository.saveAll(users);

        int maxApplyCapacity = 30;
        Lecture lecture = Lecture.createLecture("", maxApplyCapacity,
                LocalDateTime.of(2025, Month.JANUARY, 7, 15, 0),
                LocalDateTime.of(2025, Month.JANUARY, 7, 18, 0));
        lectureJpaRepository.save(lecture);

        int threadCount = 40;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(threadCount);

        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        // when
        for (int i = 0; i < threadCount; i++) {
            User user = users.get(i);
            executorService.submit(() -> {
                try {
                    lectureRegistrationService.register(user, lecture);
                    successCount.incrementAndGet();
                } catch (CustomException e) {
                    failCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executorService.shutdown();

        // then
        assertThat(successCount.get()).isEqualTo(maxApplyCapacity);
        assertThat(failCount.get()).isEqualTo(10);
    }
}
