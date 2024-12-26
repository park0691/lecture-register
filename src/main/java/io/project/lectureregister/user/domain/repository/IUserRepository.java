package io.project.lectureregister.user.domain.repository;

import io.project.lectureregister.user.domain.entity.User;

import java.util.Optional;

public interface IUserRepository {
    Optional<User> findBy(Long userId);
}
