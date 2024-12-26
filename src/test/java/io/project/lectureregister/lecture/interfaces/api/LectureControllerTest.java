package io.project.lectureregister.lecture.interfaces.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.project.lectureregister.lecture.application.LectureFacade;
import io.project.lectureregister.lecture.interfaces.api.controller.LectureController;
import io.project.lectureregister.lecture.interfaces.api.dto.LectureRegisterRequest;
import io.project.lectureregister.lecture.interfaces.api.mapper.LectureMapperImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(LectureMapperImpl.class)
@WebMvcTest(controllers = LectureController.class)
class LectureControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    LectureFacade lectureFacade;

    @DisplayName("특강을 신청한다.")
    @Test
    void register() throws Exception {
        // given
        LectureRegisterRequest request = LectureRegisterRequest.builder()
                .lectureId(1L)
                .userId(1L)
                .build();

        // when, then
        mockMvc.perform(
                        post("/api/v1/lectures/register")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Success"));
    }
}