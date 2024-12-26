package io.project.lectureregister.user.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "TEACHER")
@NoArgsConstructor
@Getter
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEACHER_ID", updatable = false)
    private Long teacherId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "REG_DT")
    private LocalDateTime regDt;

    @Column(name = "MOD_DT")
    private LocalDateTime modDt;
}