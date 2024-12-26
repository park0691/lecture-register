package io.project.lectureregister.lecture.interfaces.api.mapper;

import io.project.lectureregister.lecture.domain.command.LectureRegisterCommand;
import io.project.lectureregister.lecture.domain.entity.Lecture;
import io.project.lectureregister.lecture.interfaces.api.dto.LectureRegisterRequest;
import io.project.lectureregister.lecture.interfaces.api.dto.LectureResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LectureMapper {

    LectureRegisterCommand toCommand(LectureRegisterRequest request);

    @Mapping(source = "name", target = "lectureName")
    @Mapping(source = "teacher.teacherId", target = "teacherId")
    @Mapping(source = "teacher.name", target = "teacherName")
    @Mapping(source = "teacher.email", target = "teacherEmail")
    LectureResponse toResponse(Lecture lecture);
}
