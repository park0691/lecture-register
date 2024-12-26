package io.project.lectureregister.user.domain.service;

import io.project.lectureregister.global.common.exception.CustomException;
import io.project.lectureregister.global.common.exception.ErrorCode;
import io.project.lectureregister.user.domain.entity.User;
import io.project.lectureregister.user.domain.repository.IUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    IUserRepository userRepository;

    @InjectMocks
    UserService userService;

    @DisplayName("존재하지 않는 유저 아이디로 조회하면 예외가 발생한다.")
    @Test
    void getUserWithInvalidUserId() {
        // given
        long userId = 1L;

        // when, then
        assertThatThrownBy(() -> userService.getUser(userId))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.USER_NOT_FOUND);
    }

    @DisplayName("유저 아이디로 유저 정보를 조회한다.")
    @Test
    void getUser() {
        // given
        User user = User.createUser("PARK", "test@gmail.com", LocalDateTime.now());
        given(userRepository.findBy(any(Long.class)))
                .willReturn(Optional.of(user));

        // when
        User foundUser = userService.getUser(1L);

        // then
        assertThat(foundUser).usingRecursiveComparison()
                .isEqualTo(user);
    }
}