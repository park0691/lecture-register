package io.project.lectureregister.lecture.infrastructure.repository;

import io.project.lectureregister.lecture.domain.entity.LectureRegistration;
import io.project.lectureregister.user.domain.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LectureRegistrationQueryRepository {

    private final EntityManager em;

    public List<LectureRegistration> findByUser(User user) {
        return em.createQuery(
                        "select r from LectureRegistration r " +
                                "join fetch r.lecture " +
                                "where r.user = :user", LectureRegistration.class)
                .setParameter("user", user)
                .getResultList();
    }
}
