package io.project.lectureregister.lecture.interfaces.api.mapper;

import io.project.lectureregister.lecture.domain.command.LectureRegisterCommand;
import io.project.lectureregister.lecture.interfaces.api.dto.LectureRegisterRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LectureMapper {

    LectureRegisterCommand toCommand(LectureRegisterRequest request);
}
