package io.project.lectureregister.user.repository;

import io.project.lectureregister.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {
}
