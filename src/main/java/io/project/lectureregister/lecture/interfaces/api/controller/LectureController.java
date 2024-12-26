package io.project.lectureregister.lecture.interfaces.api.controller;

import io.project.lectureregister.global.common.dto.ApiBaseResponse;
import io.project.lectureregister.global.common.dto.ApiResponse;
import io.project.lectureregister.lecture.application.LectureFacade;
import io.project.lectureregister.lecture.interfaces.api.dto.LectureRegisterRequest;
import io.project.lectureregister.lecture.interfaces.api.dto.LectureResponse;
import io.project.lectureregister.lecture.interfaces.api.mapper.LectureMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

    @GetMapping("/available")
    public ApiResponse<List<LectureResponse>> getAvailableLectures(
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {
        return ApiResponse.ok(lectureFacade.getAvailableLectures(date));
    }

    @GetMapping("/registrations")
    public ApiResponse<List<LectureResponse>> getRegistrations(@RequestParam("userId") Long userId) {
        return ApiResponse.ok(lectureFacade.getRegisteredLectures(userId));
    }
}
