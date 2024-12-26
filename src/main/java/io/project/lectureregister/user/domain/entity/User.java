package io.project.lectureregister.user.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "USERS")
@NoArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", updatable = false)
    private Long userId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "REG_DT")
    private LocalDateTime regDt;

    @Column(name = "MOD_DT")
    private LocalDateTime modDt;

    @Builder
    private User(Long userId, String name, String email, String password, LocalDateTime regDt, LocalDateTime modDt) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.regDt = regDt;
        this.modDt = modDt;
    }

    public static User createUser(String name, String email, LocalDateTime regDt) {
        return User.builder()
                .name(name)
                .email(email)
                .regDt(regDt)
                .build();
    }
}
