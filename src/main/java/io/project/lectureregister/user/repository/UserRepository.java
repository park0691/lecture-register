package io.project.lectureregister.user.repository;

import io.project.lectureregister.user.domain.entity.User;
import io.project.lectureregister.user.domain.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository implements IUserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findBy(Long userId) {
        return userJpaRepository.findById(userId);
    }
}
