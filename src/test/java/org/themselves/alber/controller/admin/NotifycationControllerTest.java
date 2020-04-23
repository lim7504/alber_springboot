package org.themselves.alber.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.themselves.alber.config.JwtTokenProvider;
import org.themselves.alber.controller.common.dto.ImageIdWithSortNoDto;
import org.themselves.alber.controller.notifycation.dto.NotifycationRequestDto;
import org.themselves.alber.domain.User;
import org.themselves.alber.repository.NotifycationRepository;
import org.themselves.alber.repository.UserRepository;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK
        ,properties = "spring.config.location=" +
        "classpath:/application.yml" +
        ",classpath:/db.yml")
@AutoConfigureMockMvc
@Transactional
class NotifycationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    NotifycationRepository notifycationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Test
    public void testAddNotifycation() throws Exception {
        Optional<User> optionalUser = userRepository.findByEmail("aaa@aaa");
        String token = jwtTokenProvider.createToken(optionalUser.get().getEmail(), optionalUser.get().getType().name());

        NotifycationRequestDto notiDto = new NotifycationRequestDto();
        notiDto.setTitle("공지 Test");
        notiDto.setContents("공지 내용 Test");
        ImageIdWithSortNoDto imageIdSortDto = new ImageIdWithSortNoDto();
        imageIdSortDto.setImageId(10L);
        imageIdSortDto.setSortNo(2);

        ImageIdWithSortNoDto imageIdSortDto2 = new ImageIdWithSortNoDto();
        imageIdSortDto2.setImageId(11L);
        imageIdSortDto2.setSortNo(1);

        notiDto.getImageList().add(imageIdSortDto);
        notiDto.getImageList().add(imageIdSortDto2);

        mockMvc.perform(post("/admin/notifycation")
                .contentType(String.valueOf(MediaType.JSON_UTF_8))
                .accept(String.valueOf(MediaType.JSON_UTF_8))
                .header("X-AUTH-TOKEN", token)
                .content(objectMapper.writeValueAsString(notiDto)))
                .andExpect(status().isCreated())
                .andDo(print());

    }

    @Test
    public void testGetNotifycation() throws Exception{
        Optional<User> optionalUser = userRepository.findByEmail("aaa@aaa");
        String token = jwtTokenProvider.createToken(optionalUser.get().getEmail(), optionalUser.get().getType().name());

        mockMvc.perform(get("/admin/notifycation/1")
                .accept(String.valueOf(MediaType.JSON_UTF_8))
                .header("X-AUTH-TOKEN", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.title").value("타이틀"))
                .andExpect(jsonPath("data.contents").value("내용"))
                .andDo(print());

    }

}