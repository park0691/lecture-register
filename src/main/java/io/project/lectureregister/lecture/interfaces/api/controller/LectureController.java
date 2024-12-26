package io.project.lectureregister.lecture.interfaces.api.controller;

import io.project.lectureregister.global.common.dto.ApiBaseResponse;
import io.project.lectureregister.lecture.application.LectureFacade;
import io.project.lectureregister.lecture.interfaces.api.dto.LectureRegisterRequest;
import io.project.lectureregister.lecture.interfaces.api.mapper.LectureMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/lectures")
@RequiredArgsConstructor
public class LectureController {

    private final LectureFacade lectureFacade;
    private final LectureMapper lectureMapper;

    @PostMapping("/register")
    public ApiBaseResponse register(@RequestBody LectureRegisterRequest request) {
        lectureFacade.register(lectureMapper.toCommand(request));
        return ApiBaseResponse.ok();
    }
}
